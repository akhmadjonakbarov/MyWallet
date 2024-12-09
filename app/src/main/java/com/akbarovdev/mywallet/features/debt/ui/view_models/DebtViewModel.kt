package com.akbarovdev.mywallet.features.debt.ui.view_models

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallet.features.debt.domain.models.DebtModel
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import com.akbarovdev.mywallet.features.debt.domain.repository.DebtRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor(
    val repository: DebtRepository
) : ViewModel() {

    data class State(
        val total: Double = 0.0,
        val debts: List<DebtModel> = emptyList(),
        val selectedDebt: DebtModel = DebtModel.empty(),
        val error: String? = null,
        val isLoading: Boolean = false,
    )


    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    private val _isOpenDialog = mutableStateOf(false) // Tracks dialog state
    val isOpenDialog: androidx.compose.runtime.State<Boolean> = _isOpenDialog

    private val _person = MutableStateFlow(PersonModel.empty())
    val person: StateFlow<PersonModel> = _person.asStateFlow()


    fun selectPerson(person: PersonModel) {
        viewModelScope.launch {

            _person.value = person
            getAll()
            Log.e("SelectedPerson", person.toString())
        }
    }

    fun getAll() {
        viewModelScope.launch {
            loading()
            repository.getAll().collect { debts ->

                val debts = debts.filter {
                    it.personId == _person.value.id
                }
                val total = debts.filter {
                    it.isPaid == false
                }.sumOf { it.amount }
                _state.update {
                    it.copy(
                        debts = debts, isLoading = false, total = total
                    )
                }
            }
        }
    }

    fun selectDebt(debt: DebtModel, openDialog: (() -> Unit)? = null) {
        viewModelScope.launch {
            _state.update {
                it.copy(selectedDebt = debt)
            }

            openDialog?.invoke()
        }
    }


    fun pay(debt: DebtModel, notify: (() -> Unit)? = null) {
        viewModelScope.launch {
            try {

                val updatedDebt = debt.copy(
                    isPaid = true
                )
                repository.add(updatedDebt)
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to pay debt: ${e.message}")
                }
            } finally {
                getAll()
            }
            notify?.invoke()
        }
    }

    fun unPay(debt: DebtModel) {
        viewModelScope.launch {
            try {
                val updatedDebt = debt.copy(
                    isPaid = false
                )
                repository.add(updatedDebt)
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to unpay debt: ${e.message}")
                }
            } finally {
                getAll()
            }
        }
    }

    fun add(debt: DebtModel) {
        viewModelScope.launch {
            try {
                val updatedDebt = debt.copy(
                    personId = _person.value.id
                )

                repository.add(updatedDebt)
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to add debt: ${e.message}")
                }
            } finally {
                closeDialog()
                getAll()
            }
        }

    }


    fun delete(debt: DebtModel) {
        viewModelScope.launch {
            try {
                repository.delete(debt) // Call the repository to delete the debt by ID
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to delete debt: ${e.message}")
                }
            } finally {
                getAll() // Refresh the debt list for the person

            }
        }
    }

    fun update(debt: DebtModel) {
        viewModelScope.launch {
            try {

                Log.e("Debt", debt.toString())



                repository.add(debt) // Call the repository to update the debt
                _state.update {
                    it.copy(
                        selectedDebt = DebtModel.empty(),
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(error = "Failed to update debt: ${e.message}")
                }
            } finally {
                closeDialog() // Close any update dialog
                getAll() // Refresh the debt list for the person
            }
        }
    }


    fun loading() {
        _state.update {
            it.copy(isLoading = true)
        }
    }

    fun openDialog() {
        _isOpenDialog.value = true
    }

    fun closeDialog() {
        _isOpenDialog.value = false
    }

}
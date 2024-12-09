package com.akbarovdev.mywallet.features.debt.ui.view_models

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akbarovdev.mywallet.features.debt.domain.models.PersonModel
import com.akbarovdev.mywallet.features.debt.domain.repository.PersonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val personRepository: PersonRepository
) : ViewModel() {
    data class PersonState(
        val persons: List<PersonModel> = emptyList(),
        val selectedPerson: PersonModel = PersonModel.empty(),
        val isLoading: Boolean = false,
        val error: String? = null,
    )

    private val _state = MutableStateFlow(PersonState())
    val state = _state.asStateFlow()

    private val _isOpenDialog = mutableStateOf(false) // Tracks dialog state
    val isOpenDialog: State<Boolean> = _isOpenDialog

    init {
        getAll()
    }

    fun getAll() {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true) }

                personRepository.getAll().collect { persons ->
                    _state.update { it.copy(persons = persons, isLoading = false) }
                }

            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        error = "Failed to load persons: ${e.message}", isLoading = false
                    )
                }
            }
        }
    }


    fun add(person: PersonModel) {
        viewModelScope.launch {
            try {
                personRepository.add(person)
                getAll() // Refresh the list after adding
            } catch (e: Exception) {
                _state.update { it.copy(error = "Failed to add person: ${e.message}") }
            } finally {
                closeDialog() // Close dialog after operation
            }
        }
    }

    fun update(person: PersonModel) {
        viewModelScope.launch {
            try {
                val updatedPerson = person.copy(
                    id = _state.value.selectedPerson.id
                )
                personRepository.add(updatedPerson)
                getAll() // Refresh the list after updating
            } catch (e: Exception) {
                _state.update { it.copy(error = "Failed to update person: ${e.message}") }
            } finally {
                closeDialog() // Close dialog after operation
                selectPerson()
            }
        }
    }

    fun delete(person: PersonModel) {
        viewModelScope.launch {
            try {
                personRepository.delete(person)
                getAll() // Refresh the list after deletion
            } catch (e: Exception) {
                _state.update { it.copy(error = "Failed to delete person: ${e.message}") }
            } finally {
                selectPerson()
            }
        }
    }

    fun selectPerson(person: PersonModel = PersonModel.empty(), openDialog: (() -> Unit)? = null) {
        viewModelScope.launch {
            _state.update { it.copy(selectedPerson = person) }
            openDialog?.invoke()
        }
    }


    fun openDialog() {
        _isOpenDialog.value = true

    }

    fun closeDialog() {
        _isOpenDialog.value = false
        selectPerson()
    }

    fun searchByName(string: String) {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            personRepository.getAll().collect { persons ->
                val filteredPersons = persons.filter {
                    it.name.contains(
                        string, ignoreCase = true
                    )
                }
                _state.update {
                    it.copy(
                        persons = filteredPersons, isLoading = false
                    )
                }
            }
        }
    }
}
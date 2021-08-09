package com.hygge.danskordbog.db

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class DictionaryViewModel(private val repository: DictionaryRepository) : ViewModel() {

    // Using LiveData and caching what allWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val danishWord: LiveData<MutableList<Dictionary>> = repository.danishWord.asLiveData()

    val englishWord: LiveData<MutableList<Dictionary>> = repository.englishWord.asLiveData()
//    val allDanishWord: LiveData<List<String>> = repository.allDanishWord.asLiveData()

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    fun insertVocabulary(dictionary: Dictionary) = viewModelScope.launch {
        repository.insertVocabulary(dictionary)
    }
}

class WordViewModelFactory(private val repository: DictionaryRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DictionaryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DictionaryViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
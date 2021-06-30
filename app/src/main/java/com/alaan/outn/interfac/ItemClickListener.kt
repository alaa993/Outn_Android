package com.alaan.outn.interfac

import com.alaan.outn.model.AreasItem
import com.alaan.outn.model.CitiesItem
import com.alaan.outn.model.ModelCountry
import com.alaan.outn.model.StatesItem

interface ItemClickListener<T> {
    fun onClick(item: ModelCountry)
    fun state(item: StatesItem)
    fun city(item: StatesItem)
    fun area(item: AreasItem)
}

package com.alaan.outn.interfac

import com.alaan.outn.model.*

interface InterFaceShowActivityRealState {
    fun detail(model: ModelPlatform)
    fun comment(model: ModelPlatform)
    fun more(model:ModelPlatform)
    fun share(modelNews: ModelPlatform)
}


interface InterFacePartnes {
    fun more(model: ModelPartners)
}

interface InterFaceActivityNews{
    fun delet(modelNews: ModelNews)
    fun edit(modelNews: ModelNews)
    fun comment(modelNews: ModelNews)
}
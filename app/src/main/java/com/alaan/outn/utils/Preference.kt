package com.alaan.outn.utils

import com.alaan.outn.application.G
import java.util.*


object Preference {
    var isLogin: Boolean?
        get() = G.sharedPref!!.getBoolean("login", false)
        set(login) {
            G.sharedPref!!.edit().putBoolean("login", login!!).apply()
        }

    var idUser: String?
        get() = G.sharedPref!!.getString("idUser", null)
        set(idUser) {
            G.sharedPref!!.edit().putString("idUser", idUser).apply()
        }

    var saveSaleHomeTypeText: String?
        get() = G.sharedPrefSale!!.getString("saveSaleHomeTypeText", null)
        set(saveSaleHomeTypeText) {
            G.sharedPrefSale!!.edit().putString("", saveSaleHomeTypeText).apply()
        }

    fun clearPrefSale(){
        G.sharedPrefSale!!.edit().clear().apply()
    }

    var saveSaleHomeTypeId: Int?
        get() = G.sharedPrefSale!!.getInt("saveSaleHomeTypeId", 0)
        set(saveSaleHomeTypeId) {
            G.sharedPrefSale!!.edit().putInt("saveSaleHomeTypeId", saveSaleHomeTypeId!!).apply()
        }

    var saveSaleCityText: String?
        get() = G.sharedPrefSale!!.getString("saveSaleCityText", null)
        set(saveSaleCityText) {
            G.sharedPrefSale!!.edit().putString("saveSaleCityText", saveSaleCityText).apply()
        }

    var saveSaleCityId: Int?
        get() = G.sharedPrefSale!!.getInt("saveSaleCityId", 0)
        set(saveSaleCityId) {
            G.sharedPrefSale!!.edit().putInt("saveSaleCityId", saveSaleCityId!!).apply()
        }

    var saveSaleCountryText: String?
        get() = G.sharedPrefSale!!.getString("saveSaleCountryText", null)
        set(saveSaleCountryText) {
            G.sharedPrefSale!!.edit().putString("saveSaleCountryText", saveSaleCountryText).apply()
        }

    var saveSaleCountryId: Int?
        get() = G.sharedPrefSale!!.getInt("saveSaleCountryId", 0)
        set(saveSaleCountryId) {
            G.sharedPrefSale!!.edit().putInt("saveSaleCountryId", saveSaleCountryId!!).apply()
        }

    var saveSaleRoom: String?
        get() = G.sharedPrefSale!!.getString("saveSaleRoom", null)
        set(saveSaleRoom) {
            G.sharedPrefSale!!.edit().putString("saveSaleRoom", saveSaleRoom).apply()
        }

    var saveSaleSpace: String?
        get() = G.sharedPrefSale!!.getString("saveSaleSpace", null)
        set(saveSaleSpace) {
            G.sharedPrefSale!!.edit().putString("saveSaleSpace", saveSaleSpace).apply()
        }

    var saveSalePrice: String?
        get() = G.sharedPrefSale!!.getString("saveSalePrice", null)
        set(saveSalePrice) {
            G.sharedPrefSale!!.edit().putString("saveSalePrice", saveSalePrice).apply()
        }

    var saveSaleUnitText: String?
        get() = G.sharedPrefSale!!.getString("saveSaleUnitText", null)
        set(saveSaleUnitText) {
            G.sharedPrefSale!!.edit().putString("saveSaleUnitText", saveSaleUnitText).apply()
        }

    var saveSaleUnitId: Int?
        get() = G.sharedPrefSale!!.getInt("saveSaleUnitId", 0)
        set(saveSaleUnitId) {
            G.sharedPrefSale!!.edit().putInt("saveSaleUnitId", saveSaleUnitId!!).apply()
        }

    var saveSalePhone: String?
        get() = G.sharedPrefSale!!.getString("saveSalePhone", null)
        set(saveSalePhone) {
            G.sharedPrefSale!!.edit().putString("saveSalePhone", saveSalePhone).apply()
        }

    var saveSaleDescription: String?
        get() = G.sharedPrefSale!!.getString("saveSaleDescription", null)
        set(saveSaleDescription) {
            G.sharedPrefSale!!.edit().putString("saveSaleDescription", saveSaleDescription).apply()
        }

    var saveSaleAboutTheNeigh: String?
        get() = G.sharedPrefSale!!.getString("saveSaleAboutTheNeigh", null)
        set(saveSaleAboutTheNeigh) {
            G.sharedPrefSale!!.edit().putString("saveSaleAboutTheNeigh", saveSaleAboutTheNeigh).apply()
        }

    var saveSaleImage1: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage1", null)
        set(saveSaleImage1) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage1", saveSaleImage1).apply()
        }

    var saveSaleImagePath1: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath1", null)
        set(saveSaleImagePath1) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath1", saveSaleImagePath1).apply()
        }

    var saveSaleImage2: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage2", null)
        set(saveSaleImage2) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage2", saveSaleImage2).apply()
        }

    var saveSaleImagePath2: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath2", null)
        set(saveSaleImagePath2) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath2", saveSaleImagePath2).apply()
        }

    var saveSaleImage3: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage3", null)
        set(saveSaleImage3) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage3", saveSaleImage3).apply()
        }
    var saveSaleImagePath3: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath3", null)
        set(saveSaleImagePath3) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath3", saveSaleImagePath3).apply()
        }

    var saveSaleImage4: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage4", null)
        set(saveSaleImage4) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage4", saveSaleImage4).apply()
        }

    var saveSaleImagePath4: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath4", null)
        set(saveSaleImagePath4) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath4", saveSaleImagePath4).apply()
        }

    var saveSaleImage5: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage5", null)
        set(saveSaleImage5) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage5", saveSaleImage5).apply()
        }

    var saveSaleImagePath5: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath5", null)
        set(saveSaleImagePath5) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath5", saveSaleImagePath5).apply()
        }

    var saveSaleImage6: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage6", null)
        set(saveSaleImage6) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage6", saveSaleImage6).apply()
        }

    var saveSaleImagePath6: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath6", null)
        set(saveSaleImagePath6) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath6", saveSaleImagePath6).apply()
        }

    var saveSaleImage7: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage7", null)
        set(saveSaleImage7) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage7", saveSaleImage7).apply()
        }

    var saveSaleImagePath7: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath7", null)
        set(saveSaleImagePath7) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath7", saveSaleImagePath7).apply()
        }

    var saveSaleImage8: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage8", null)
        set(saveSaleImage8) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage8", saveSaleImage8).apply()
        }

    var saveSaleImagePath8: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath8", null)
        set(saveSaleImagePath8) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath8", saveSaleImagePath8).apply()
        }

    var saveSaleImage9: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage9", null)
        set(saveSaleImage9) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage9", saveSaleImage9).apply()
        }

    var saveSaleImagePath9: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath9", null)
        set(saveSaleImagePath9) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath9", saveSaleImagePath9).apply()
        }

    var saveSaleImage10: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage10", null)
        set(saveSaleImage10) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage10", saveSaleImage10).apply()
        }

    var saveSaleImagePath10: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath10", null)
        set(saveSaleImagePath10) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath10", saveSaleImagePath10).apply()
        }

    var saveSaleImage11: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage11", null)
        set(saveSaleImage11) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage11", saveSaleImage11).apply()
        }

    var saveSaleImagePath11: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath11", null)
        set(saveSaleImagePath11) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath11", saveSaleImagePath11).apply()
        }

    var saveSaleImage12: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImage12", null)
        set(saveSaleImage12) {
            G.sharedPrefSale!!.edit().putString("saveSaleImage12", saveSaleImage12).apply()
        }

    var saveSaleImagePath12: String?
        get() = G.sharedPrefSale!!.getString("saveSaleImagePath12", null)
        set(saveSaleImagePath12) {
            G.sharedPrefSale!!.edit().putString("saveSaleImagePath12", saveSaleImagePath12).apply()
        }

    var saveRentHomeTypeText: String?
        get() = G.sharedPrefRent!!.getString("saveRentHomeTypeText", null)
        set(saveRentHomeTypeText) {
            G.sharedPrefRent!!.edit().putString("", saveRentHomeTypeText).apply()
        }

    fun clearPrefRent(){
        G.sharedPrefRent!!.edit().clear().apply()
    }

    var saveRentHomeTypeId: Int?
        get() = G.sharedPrefRent!!.getInt("saveRentHomeTypeId", 0)
        set(saveRentHomeTypeId) {
            G.sharedPrefRent!!.edit().putInt("saveRentHomeTypeId", saveRentHomeTypeId!!).apply()
        }

    var saveRentCityText: String?
        get() = G.sharedPrefRent!!.getString("saveRentCityText", null)
        set(saveRentCityText) {
            G.sharedPrefRent!!.edit().putString("saveRentCityText", saveRentCityText).apply()
        }

    var saveRentCityId: Int?
        get() = G.sharedPrefRent!!.getInt("saveRentCityId", 0)
        set(saveRentCityId) {
            G.sharedPrefRent!!.edit().putInt("saveRentCityId", saveRentCityId!!).apply()
        }

    var saveRentCountryText: String?
        get() = G.sharedPrefRent!!.getString("saveRentCountryText", null)
        set(saveRentCountryText) {
            G.sharedPrefRent!!.edit().putString("saveRentCountryText", saveRentCountryText).apply()
        }

    var saveRentCountryId: Int?
        get() = G.sharedPrefRent!!.getInt("saveRentCountryId", 0)
        set(saveRentCountryId) {
            G.sharedPrefRent!!.edit().putInt("saveRentCountryId", saveRentCountryId!!).apply()
        }

    var saveRentRoom: String?
        get() = G.sharedPrefRent!!.getString("saveRentRoom", null)
        set(saveRentRoom) {
            G.sharedPrefRent!!.edit().putString("saveRentRoom", saveRentRoom).apply()
        }

    var saveRentSpace: String?
        get() = G.sharedPrefRent!!.getString("saveRentSpace", null)
        set(saveRentSpace) {
            G.sharedPrefRent!!.edit().putString("saveRentSpace", saveRentSpace).apply()
        }

    var saveRentYearly: String?
        get() = G.sharedPrefRent!!.getString("saveRentYearly", null)
        set(saveRentYearly) {
            G.sharedPrefRent!!.edit().putString("saveRentYearly", saveRentYearly).apply()
        }

    var saveRentMonthly: String?
        get() = G.sharedPrefRent!!.getString("saveRentMonthly", null)
        set(saveRentMonthly) {
            G.sharedPrefRent!!.edit().putString("saveRentMonthly", saveRentMonthly).apply()
        }

   /* var saveRentPrice: String?
        get() = G.sharedPrefRent!!.getString("saveRentPrice", null)
        set(saveRentPrice) {
            G.sharedPrefRent!!.edit().putString("saveRentPrice", saveRentPrice).apply()
        }*/

    var saveRentUnitText: String?
        get() = G.sharedPrefRent!!.getString("saveRentUnitText", null)
        set(saveRentUnitText) {
            G.sharedPrefRent!!.edit().putString("saveRentUnitText", saveRentUnitText).apply()
        }

    var saveRentUnitId: Int?
        get() = G.sharedPrefRent!!.getInt("saveRentUnitId", 0)
        set(saveRentUnitId) {
            G.sharedPrefRent!!.edit().putInt("saveRentUnitId", saveRentUnitId!!).apply()
        }

    var saveRentPhone: String?
        get() = G.sharedPrefRent!!.getString("saveRentPhone", null)
        set(saveRentPhone) {
            G.sharedPrefRent!!.edit().putString("saveRentPhone", saveRentPhone).apply()
        }

    var saveRentDescription: String?
        get() = G.sharedPrefRent!!.getString("saveRentDescription", null)
        set(saveRentDescription) {
            G.sharedPrefRent!!.edit().putString("saveRentDescription", saveRentDescription).apply()
        }

    var saveRentAboutTheNeigh: String?
        get() = G.sharedPrefRent!!.getString("saveRentAboutTheNeigh", null)
        set(saveRentAboutTheNeigh) {
            G.sharedPrefRent!!.edit().putString("saveRentAboutTheNeigh", saveRentAboutTheNeigh).apply()
        }

    var saveRentImage1: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage1", null)
        set(saveRentImage1) {
            G.sharedPrefRent!!.edit().putString("saveRentImage1", saveRentImage1).apply()
        }

    var saveRentImagePath1: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath1", null)
        set(saveRentImagePath1) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath1", saveRentImagePath1).apply()
        }

    var saveRentImage2: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage2", null)
        set(saveRentImage2) {
            G.sharedPrefRent!!.edit().putString("saveRentImage2", saveRentImage2).apply()
        }

    var saveRentImagePath2: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath2", null)
        set(saveRentImagePath2) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath2", saveRentImagePath2).apply()
        }

    var saveRentImage3: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage3", null)
        set(saveRentImage3) {
            G.sharedPrefRent!!.edit().putString("saveRentImage3", saveRentImage3).apply()
        }
    var saveRentImagePath3: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath3", null)
        set(saveRentImagePath3) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath3", saveRentImagePath3).apply()
        }

    var saveRentImage4: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage4", null)
        set(saveRentImage4) {
            G.sharedPrefRent!!.edit().putString("saveRentImage4", saveRentImage4).apply()
        }

    var saveRentImagePath4: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath4", null)
        set(saveRentImagePath4) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath4", saveRentImagePath4).apply()
        }

    var saveRentImage5: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage5", null)
        set(saveRentImage5) {
            G.sharedPrefRent!!.edit().putString("saveRentImage5", saveRentImage5).apply()
        }

    var saveRentImagePath5: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath5", null)
        set(saveRentImagePath5) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath5", saveRentImagePath5).apply()
        }

    var saveRentImage6: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage6", null)
        set(saveRentImage6) {
            G.sharedPrefRent!!.edit().putString("saveRentImage6", saveRentImage6).apply()
        }

    var saveRentImagePath6: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath6", null)
        set(saveRentImagePath6) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath6", saveRentImagePath6).apply()
        }

    var saveRentImage7: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage7", null)
        set(saveRentImage7) {
            G.sharedPrefRent!!.edit().putString("saveRentImage7", saveRentImage7).apply()
        }

    var saveRentImagePath7: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath7", null)
        set(saveRentImagePath7) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath7", saveRentImagePath7).apply()
        }

    var saveRentImage8: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage8", null)
        set(saveRentImage8) {
            G.sharedPrefRent!!.edit().putString("saveRentImage8", saveRentImage8).apply()
        }

    var saveRentImagePath8: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath8", null)
        set(saveRentImagePath8) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath8", saveRentImagePath8).apply()
        }

    var saveRentImage9: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage9", null)
        set(saveRentImage9) {
            G.sharedPrefRent!!.edit().putString("saveRentImage9", saveRentImage9).apply()
        }

    var saveRentImagePath9: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath9", null)
        set(saveRentImagePath9) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath9", saveRentImagePath9).apply()
        }

    var saveRentImage10: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage10", null)
        set(saveRentImage10) {
            G.sharedPrefRent!!.edit().putString("saveRentImage10", saveRentImage10).apply()
        }

    var saveRentImagePath10: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath10", null)
        set(saveRentImagePath10) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath10", saveRentImagePath10).apply()
        }

    var saveRentImage11: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage11", null)
        set(saveRentImage11) {
            G.sharedPrefRent!!.edit().putString("saveRentImage11", saveRentImage11).apply()
        }

    var saveRentImagePath11: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath11", null)
        set(saveRentImagePath11) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath11", saveRentImagePath11).apply()
        }

    var saveRentImage12: String?
        get() = G.sharedPrefRent!!.getString("saveRentImage12", null)
        set(saveRentImage12) {
            G.sharedPrefRent!!.edit().putString("saveRentImage12", saveRentImage12).apply()
        }

    var saveRentImagePath12: String?
        get() = G.sharedPrefRent!!.getString("saveRentImagePath12", null)
        set(saveRentImagePath12) {
            G.sharedPrefRent!!.edit().putString("saveRentImagePath12", saveRentImagePath12).apply()
        }

    var phone: String?
        get() = G.sharedPref!!.getString("phone", "")
        set(phone) {
            G.sharedPref!!.edit().putString("phone", phone).apply()
        }

    var firstName: String?
        get() = G.sharedPref?.getString("firstName", "")
        set(firstName) {
            G.sharedPref!!.edit().putString("firstName", firstName).apply()
        }

    var address: String?
        get() = G.sharedPref?.getString("address", "")
        set(address) {
            G.sharedPref!!.edit().putString("address", address).apply()
        }

    var image: String?
        get() = G.sharedPref?.getString("image", "noImage")
        set(image) {
            G.sharedPref!!.edit().putString("image", image).apply()
        }

    var lastName: String?
        get() = G.sharedPref!!.getString("lastName", "")
        set(lastName) {
            G.sharedPref!!.edit().putString("lastName", lastName).apply()
        }

    var email: String?
        get() = G.sharedPref!!.getString("email", "")
        set(email) {
            G.sharedPref!!.edit().putString("email", email).apply()
        }

    var token: String?
        get() = G.sharedPref!!.getString("token", null)
        set(token) {
            G.sharedPref!!.edit().putString("token", token).apply()
        }

    val idLanguage: Int
        get() = G.sharedPref!!.getInt("idLanguage", 1)

    var deviceId: String?
        get() {
            val idDevice: String? = G.sharedPref!!.getString("deviceId", null)
            if (idDevice == null) {
                deviceId = UUID.randomUUID().toString()
            }
            return G.sharedPref!!.getString("deviceId", UUID.randomUUID().toString())
        }
        set(deviceId) {
            G.sharedPref!!.edit().putString("deviceId", deviceId).apply()
        }

    var business_name: String?
        get() = G.sharedPref!!.getString("business_name", null)
        set(idUser) {
            G.sharedPref!!.edit().putString("business_name", idUser).apply()
        }

    var real_state_image: String?
        get() = G.sharedPref!!.getString("real_state_image ", null)
        set(idUser) {
            G.sharedPref!!.edit().putString("real_state_image ", idUser).apply()
        }

    var vision: String?
        get() = G.sharedPref!!.getString("vision ", "")
        set(idUser) {
            G.sharedPref!!.edit().putString("vision ", idUser).apply()
        }

    var real_state_description: String?
        get() = G.sharedPref!!.getString("real_state_description ", null)
        set(idUser) {
            G.sharedPref!!.edit().putString("real_state_description ", idUser).apply()
        }

    var language: String?
        get() = G.sharedPref!!.getString("language", "en")
        set(value) {
            G.sharedPref!!.edit().putString("language", value).apply()
        }

    var tokenFierbase: String?
        get() = G.sharedPref!!.getString("tokenFierbase", "")
        set(value) {
            G.sharedPref!!.edit().putString("tokenFierbase", value).apply()
        }

    var idPushNotfcation: String?
        get() = G.sharedPref!!.getString("idPushNotfcation", "")
        set(value) {
            G.sharedPref!!.edit().putString("idPushNotfcation", value).apply()
        }


    fun logOut() {
        G.sharedPref!!.edit().remove("token").apply()
        G.sharedPref!!.edit().remove("idUser").apply()
        G.sharedPref!!.edit().remove("image").apply()
        G.sharedPref!!.edit().remove("lastName").apply()
        G.sharedPref!!.edit().remove("email").apply()
        G.sharedPref!!.edit().remove("firstName").apply()
        G.sharedPref!!.edit().remove("phone").apply()
        G.sharedPref!!.edit().remove("login").apply()
        G.sharedPref!!.edit().remove("business_name").apply()
    }
}
package ali.mert.atmmnerede

import com.google.gson.annotations.SerializedName

data class GetBankListResponseModel(
    @SerializedName("resultList") val Bankalar: List<BankaModel>
)

data class BankaModel(
    val id: String,
    val name: String,
    val iconPath: String,
    val logoPath: String,
)
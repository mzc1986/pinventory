package ds.pinvent.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Product (

    @PrimaryKey(autoGenerate = true)
    val uid: Int?,

    @ColumnInfo(name = "product_name")
    val productName: String?,

    @ColumnInfo(name = "product_description")
    val productDescription: String?,

    @ColumnInfo(name = "product_price")
    val productPrice: String?,

    @ColumnInfo(name = "product_image_uri")
    val productImageUri: String?

) : Serializable {
    constructor(mName: String, mDescription: String, mPrice: String, mImageUri: String) : this(null, mName, mDescription, mPrice, mImageUri) {

    }
}
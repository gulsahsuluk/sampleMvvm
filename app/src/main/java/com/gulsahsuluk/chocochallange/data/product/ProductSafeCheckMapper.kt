package com.gulsahsuluk.chocochallange.data.product

import com.gulsahsuluk.chocochallange.data.product.model.Product
import com.gulsahsuluk.chocochallange.data.product.model.ProductRaw
import com.gulsahsuluk.chocochallange.data.exceptions.EssentialParamMissingException
import dagger.Reusable
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * It is safe entity mapper. It check every parameter.
 * It thrown exception when a parameter is missing but the real use case would probably be different.
 * It might thrown exception when just mandatory parameter missing.
 * That way we can trust the store when we get the data. We don't need to check or worried about anything.
 * */
@Reusable
class ProductSafeCheckMapper @Inject constructor() :
    Function<List<ProductRaw>, List<Product>> {

    companion object {

        const val guidPram = "guid"
        const val nameParam = "name"
        const val priceParam = "price"
        const val pictureParam = "picture"
        const val descriptionParam = "description"
    }

    override fun apply(productsRaw: List<ProductRaw>): List<Product> {
        return assertEssentialParams(productsRaw)
    }


    private fun assertEssentialParams(productsRaw: List<ProductRaw>): List<Product> {

        val missingParamsList = mutableListOf<String>()

        productsRaw.forEach {

            if (it.guid.isNullOrEmpty()) {
                missingParamsList.add(guidPram)
            }

            if (it.name.isNullOrEmpty()) {
                missingParamsList.add(nameParam)
            }

            if (it.price == null) {
                missingParamsList.add(priceParam)
            }

            if (it.picture.isNullOrEmpty()) {
                missingParamsList.add(pictureParam)
            }

            if (it.description.isNullOrEmpty()) {
                missingParamsList.add(descriptionParam)
            }
        }

        if (missingParamsList.isNotEmpty()) {
            throw EssentialParamMissingException(
                missingParamsList.toString(),
                productsRaw
            )
        } else {
            return transform(productsRaw)
        }
    }

    private fun transform(data: List<ProductRaw>): List<Product> {
        return data.map {
            Product(
                id = it.guid!!,
                name = it.name!!,
                price = it.price!!,
                picture = it.picture!!,
                description = it.description!!
            )
        }
    }
}
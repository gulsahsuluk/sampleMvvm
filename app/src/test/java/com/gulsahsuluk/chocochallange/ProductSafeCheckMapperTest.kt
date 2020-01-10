package com.gulsahsuluk.chocochallange

import com.google.common.truth.Truth
import com.gulsahsuluk.chocochallange.data.product.ProductSafeCheckMapper
import com.gulsahsuluk.chocochallange.data.product.model.ProductRaw
import com.gulsahsuluk.chocochallange.data.exceptions.EssentialParamMissingException
import io.mockk.impl.annotations.InjectMockKs
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class ProductSafeCheckMapperTest {

    @Rule
    @JvmField
    val thrown: ExpectedException = ExpectedException.none()

    @InjectMockKs
    private var mapper = ProductSafeCheckMapper()

    @Test
    fun `should mapping successfully`() {
        val raw = createProductRawObject()

        val mapped = mapper.apply(raw)

        Truth.assertThat(mapped[0].id).isEqualTo(raw[0].guid)
        Truth.assertThat(mapped[0].name).isEqualTo(raw[0].name)
        Truth.assertThat(mapped[0].picture).isEqualTo(raw[0].picture)
        Truth.assertThat(mapped[0].description).isEqualTo(raw[0].description)
    }


    @Test
    fun`should exception thrown correct params when essential params missing`() {
        thrown.expect(EssentialParamMissingException::class.java)
        thrown.expectMessage("guid, price")

        mapper.apply(listOf(
            ProductRaw(
                guid = null,
                name = "Biolive",
                price = null,
                picture = "http://placehold.it/32x32",
                description = "Cillum consectetur ullamco non veniam id aute culpa Lorem exercitation qui ut do."
            )))
    }

    private fun createProductRawObject(): List<ProductRaw> {
        return listOf(
            ProductRaw(
                guid = "13085ace-fd17-4560-9614-426fd45823cd",
                name = "Biolive",
                price = 8555,
                picture = "http://placehold.it/32x32",
                description = "Cillum consectetur ullamco non veniam id aute culpa Lorem exercitation qui ut do."
            )
        )
    }
}
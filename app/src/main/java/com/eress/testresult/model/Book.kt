package com.eress.testresult.model

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

class Book(
        var error: String = "",
        var title: String = "",
        var subtitle: String = "",
        var authors: String = "",
        var publisher: String = "",
        var isbn10: String = "",
        var isbn13: String = "",
        var pages: String = "",
        var year: String = "",
        var rating: String = "",
        var desc: String = "",
        var price: String = "",
        var image: String = "",
        var url: String = ""
)
{
    override fun toString(): String {
        //return "Book(error='$error', title='$title', subtitle='$subtitle', authors='$authors', publisher='$publisher', isbn10='$isbn10', isbn13='$isbn13', pages='$pages', year='$year', rating='$rating', desc='$desc', price='$price', image='$image', url='$url')"
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
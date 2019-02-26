package com.eress.testresult.model

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

class Search(
        var error: String = "",
        var total: String = "",
        var page: String = "",
        var books: List<Book> = arrayListOf()
)
{
    override fun toString(): String {
        //return "Search(error='$error', total='$total', page='$page', books=$books)"
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
package com.eress.testresult.model

import org.apache.commons.lang3.builder.ToStringBuilder
import org.apache.commons.lang3.builder.ToStringStyle

class New(
        var total: String = "",
        var books: List<Book> = arrayListOf()
)
{
    override fun toString(): String {
        //return "New(total='$total', books=$books)"
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE)
    }
}
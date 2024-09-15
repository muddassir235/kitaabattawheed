package com.app.kitaabattawheed

data class Chapter(val chapterNo: String = "",
                   val title     : String = "",
                   val content   : Array<String>? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chapter

        if (chapterNo != other.chapterNo) return false
        if (title != other.title) return false
        if (content != null) {
            if (other.content == null) return false
            if (!content.contentEquals(other.content)) return false
        } else if (other.content != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chapterNo.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (content?.contentHashCode() ?: 0)
        return result
    }
}
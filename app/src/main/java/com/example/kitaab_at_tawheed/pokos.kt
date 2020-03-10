package com.example.kitaab_at_tawheed

data class Chapter(val chapter_no: String = "",
                   val title     : String = "",
                   val content   : Array<String>? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Chapter

        if (chapter_no != other.chapter_no) return false
        if (title != other.title) return false
        if (content != null) {
            if (other.content == null) return false
            if (!content.contentEquals(other.content)) return false
        } else if (other.content != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = chapter_no.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + (content?.contentHashCode() ?: 0)
        return result
    }
}
package com.example.Lista2JP

/**
 * author: Aleksandra Bujacz
 */
//pisząc kod, użyto pomocy ze strony: https://kotlinlang.org/docs/object-declarations.html
// do translacji skorzystano ze strony: https://pl.wikibooks.org/wiki/Aneks/Tabela_kodon%C3%B3w_dla_mRNA

//Tworzenie klasy DNASequence
class DNASequence(val identifier: String, var data: String) {
    companion object {
        // definiowanie zbioru dozwolonych znaków do użycia w celu weryfikacji sekwencji
        val VALID_CHARS = setOf('A', 'T', 'C', 'G')
    }

    val length: Int = data.length  // długość sekwencji zasad

    // Metoda zwracajaca reprezentacje tekstowa w formacie FASTA
    fun toStringFormat(): String {
        return ">$identifier\n$data" // format FASTA
    }
    // Metoda zmieniajaca zasadę na zadanej pozycji w sekwencji DNA na zasadę podaną jako value
    fun mutate(position: Int, value: Char) {
        require(value in VALID_CHARS) { "To nieprawidlowy znak: $value" }
        data = data.substring(0, position) + value + data.substring(position + 1)
    }

    // Metoda zwracajaca pozycję zadanego motywu w sekwencji DNA
    fun findMotif(motif: String): Int {
        return data.indexOf(motif)
    }

    //Metoda zwracajaca nić komplementarną do sekwencji DNA
    fun complement(): String {
        val nicKomplementarna = mapOf('A' to 'T', 'T' to 'A', 'C' to 'G', 'G' to 'C')
        return data.map { nicKomplementarna[it] ?: error("To nieprawidlowa zasada: $it") }.joinToString("")
    }

    // Metoda zwracajaca obiekt klasy RNASequence reprezentujący wynik transkrypcji sekwencji DNA
    fun wynikTranskrypji(): RNASequence {
        val RNA = data.replace('T', 'U')
        return RNASequence(identifier, RNA)
    }
}

// Tworzenie klasy RNASequence
class RNASequence(val identifier: String, var data: String) {
    companion object {
        // definiowanie zbioru dozwolonych znaków do użycia w celu weryfikacji sekwencji
        val VALID_CHARS = setOf('A', 'U', 'C', 'G')
    }

    val length: Int = data.length  // długość sekwencji zasad

    // Metoda zwracajaca reprezentacje tekstowa w formacie FASTA
    fun toStringFormat(): String {
        return ">$identifier\n$data" // format FASTA
    }

    // Metoda zmieniająca zasadę na zadanej pozycji w sekwencji RNA na zasadę podaną jako value
    fun mutate(position: Int, value: Char) {
        require(value in VALID_CHARS) { "To nieprawidlowy znak: $value" }
        data = data.substring(0, position) + value + data.substring(position + 1)
    }

    // Metoda zwracajaca pozycję zadanego motywu w sekwencji RNA
    fun findMotif(motif: String): Int {
        return data.indexOf(motif)
    }

    // Metoda zwracająca obiekt klasy ProteinSequence reprezentujący wynik translacji sekwencji RNA
    fun transcribe(): ProteinSequence {
        val translacjaRNA = mapOf(
            "UUU" to 'F', "UUC" to 'F', // Fenyloalanina
            "UUA" to 'L', "UUG" to 'L', "CUU" to 'L', "CUC" to 'L', "CUA" to 'L', "CUG" to 'L', // Leucyna
            "AUU" to 'I', "AUC" to 'I', "AUA" to 'I', // Izoleucyna
            "AUG" to 'M', // Metionina (start)
            "GUU" to 'V', "GUC" to 'V', "GUA" to 'V', "GUG" to 'V', // Walina

            "UCU" to 'S', "UCC" to 'S', "UCA" to 'S', "UCG" to 'S', "AGU" to 'S', "AGC" to 'S', // Seryna
            "CCU" to 'P', "CCC" to 'P', "CCA" to 'P', "CCG" to 'P', // Prolina
            "ACU" to 'T', "ACC" to 'T', "ACA" to 'T', "ACG" to 'T', // Treonina
            "GCU" to 'A', "GCC" to 'A', "GCA" to 'A', "GCG" to 'A', // Alanina

            "UAU" to 'Y', "UAC" to 'Y', // Tyrozyna
            "UAA" to '*', "UAG" to '*', "UGA" to '*', // Kodony STOP
            "CAU" to 'H', "CAC" to 'H', // Histydyna
            "CAA" to 'Q', "CAG" to 'Q', // Glutamina
            "AAU" to 'N', "AAC" to 'N', // Asparagina
            "AAA" to 'K', "AAG" to 'K', // Lizyna
            "GAU" to 'D', "GAC" to 'D', // Kwas asparaginowy
            "GAA" to 'E', "GAG" to 'E', // Kwas glutaminowy

            "UGU" to 'C', "UGC" to 'C', // Cysteina
            "UGG" to 'W', // Tryptofan
            "CGU" to 'R', "CGC" to 'R', "CGA" to 'R', "CGG" to 'R', "AGA" to 'R', "AGG" to 'R', // Arginina
            "GGU" to 'G', "GGC" to 'G', "GGA" to 'G', "GGG" to 'G' // Glicyna
        )
        val bialko = StringBuilder() //laczenie w sekwencje ciagu znakow
        for (i in 0 until data.length - 2 step 3) {
            val kodon = data.substring(i, i + 3)
            bialko.append(translacjaRNA[kodon] ?: '?') // kodon, którego nie ma w liście
        }
        return ProteinSequence(identifier, bialko.toString())
    }
}

// Tworzenie klasy ProteinSequence
class ProteinSequence(val identifier: String, var data: String) {
    companion object {
        // definiowanie zbioru dozwolonych znaków do użycia w celu weryfikacji sekwencji
        val VALID_CHARS = ("ACDEFGHIKLMNPQRSTVWY*".toSet()) // * jako kodon STOP
    }

    val length: Int = data.length  // długość sekwencji aminokwasow

    // Metoda zwracajaca reprezentacje tekstowa w formacie FASTA
    fun toStringFormat(): String {
        return ">$identifier\n$data" // format FASTA
    }

    // Metoda zmieniająca aminokwas na zadanej pozycji w sekwencji białka na aminokwas podany jako value
    fun mutate(position: Int, value: Char) {
        require(value in VALID_CHARS) { "Invalid character: $value" }
        data = data.substring(0, position) + value + data.substring(position + 1)
    }

    // Metoda zwracająca pozycję zadanego motywu w sekwencji białka
    fun findMotif(motif: String): Int {
        return data.indexOf(motif)
    }
}

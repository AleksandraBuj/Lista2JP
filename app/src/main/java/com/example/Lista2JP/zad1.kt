package com.example.Lista2JP
import java.lang.Math.pow

/**
@author: Aleksandra Bujacz
 */
//pisząc kod, użyto pomocy ze strony: https://androidcode.pl/blog/kotlin/klasy/
//do napisania czesci kodu z operatorami skorzystano z: https://kotlinlang.org/docs/collection-plus-minus.html

class Wielomian(lista_wspolczynnikow: List<Double>) {

    //Tworzenie listy przechowujacej wspolczynniki
    var wspolczynniki = lista_wspolczynnikow.toMutableList()

    // Metoda zwracająca stopień wielomianu
    fun stopien(): Int {
        return wspolczynniki.size - 1
    }

    // Metoda zwracająca tekstową reprezentację wielomianu
    fun toStringWielomian(): String {
        val stopienWielomianu = stopien()
        val elementy = mutableListOf<String>()

        // Pętla przechodząca po wszystkich współczynnikach
        for (i in 0..stopienWielomianu) {
            val wspolczynnik = wspolczynniki[i]
            val potega = stopienWielomianu - i

            // Warunek dla współczynnika różnego od 0
            if (wspolczynnik != 0.0) {
                val skladnik = when {
                    potega == 0 -> "$wspolczynnik" // Dla potęgi = 0, będzie wywołana sama liczba
                    potega == 1 -> "${wspolczynnik}x" // Dla potęgi = 1, współczynnik będzie: x zamiast x^1
                    else -> "${wspolczynnik}x^$potega"
                }
                elementy.add(skladnik) // Dodanie wyrazu do listy
            }
        }
        // Łączenie wyrazów, jeśli otrzymamy ciąg znaków + (-), następuje zamiana na -
        return elementy.joinToString(" + ").replace(" + -", " - ")
    }

    // Metoda obliczająca wartość wielomianu dla zadanego x
    fun wartoscWielomianu(x: Double): Double {
        var rezultat = 0.0
        val stopienWielomianu = stopien()

        // Pętla przechodząca po wszystkich współczynnikach + obliczajaca wielomiany
        for (j in 0..stopienWielomianu) {
            val wspolczynnik = wspolczynniki[j]
            val potega = stopienWielomianu - j
            rezultat += wspolczynnik * pow(x, potega.toDouble()) // Obliczanie składników wielomianu
        }
        return rezultat
    }

    // Operator dodawania dla wielomianów
    operator fun plus(drugiWielomian: Wielomian): Wielomian {
        // Wyznaczenie maksymalnej długości spośród obu list współczynników
        val maksimum = maxOf(this.wspolczynniki.size, drugiWielomian.wspolczynniki.size)
        // Utworzenie nowej listy o długości maksymalnej, wypelnionej zerami
        val result = MutableList(maksimum) { 0.0 }

        // Dodanie współczynników z bieżącego obiektu do nowej listy
        for (i in 0 until this.wspolczynniki.size) {
            result[i] += this.wspolczynniki[i]
        }

        // Dodanie współczynników z drugiego wielomianu do nowej listy
        for (i in 0 until drugiWielomian.wspolczynniki.size) {
            result[i] += drugiWielomian.wspolczynniki[i]
        }

        // Zwrócenie nowego obiektu: Wielomian, który posiada obliczone wspolczynniki
        return Wielomian(result)
    }

    // Operator odejmowania dla wielomianów
    operator fun minus(drugiWielomian: Wielomian): Wielomian {
        // Wyznaczenie maksymalnej długości spośród obu list współczynników
        val maksimum = maxOf(this.wspolczynniki.size, drugiWielomian.wspolczynniki.size)
        // Utworzenie nowej listy o długości maksymalnej, wypelnionej zerami
        val result = MutableList(maksimum) { 0.0 }

        // Dodanie współczynników z bieżącego obiektu do nowej listy
        for (i in 0 until this.wspolczynniki.size) {
            result[i] += this.wspolczynniki[i]
        }

        // Dodanie współczynników z drugiego wielomianu do nowej listy
        for (i in 0 until drugiWielomian.wspolczynniki.size) {
            result[i] -= drugiWielomian.wspolczynniki[i]
        }

        // Zwrócenie nowego obiektu: Wielomian, który posiada obliczone wspolczynniki
        return Wielomian(result)
    }

    // Operator mnożenia dla wielomianów
    operator fun times(drugiWielomian: Wielomian): Wielomian {
        // Obliczenie rozmiaru nowego wielomianu
        val resultSize = this.wspolczynniki.size + drugiWielomian.wspolczynniki.size - 1
        // Utworzenie nowej listy wypelnionej zerami
        val result = MutableList(resultSize) { 0.0 }

        // Wykonanie mnozenia pomiedzy każdym ze składników pierwszego oraz drugiego wielomianu,
        // a nastepnie dodanie wyniku do listy
        for (i in this.wspolczynniki.indices) {
            for (j in drugiWielomian.wspolczynniki.indices) {
                result[i + j] += this.wspolczynniki[i] * drugiWielomian.wspolczynniki[j]
            }
        }

        // Zwrócenie nowego obiektu: Wielomian, który posiada obliczone wspolczynniki
        return Wielomian(result)
    }

    // Operator zlozony += dla wielomianów
    operator fun plusAssign(drugiWielomian: Wielomian) {
        // Wyznaczenie maksymalnej długości spośród obu list współczynników
        val maksimum = maxOf(this.wspolczynniki.size, drugiWielomian.wspolczynniki.size)
        // Utworzenie nowej listy wypelnionej zerami
        val result = MutableList(maksimum) { 0.0 }

        // Dodanie współczynników z bieżącego wielomianu
        for (i in 0 until this.wspolczynniki.size) {
            result[i] += this.wspolczynniki[i]
        }

        // Dodanie współczynników z drugiego wielomianu
        for (i in 0 until drugiWielomian.wspolczynniki.size) {
            result[i] += drugiWielomian.wspolczynniki[i]
        }

        // Zastąpienie współczynników nowo obliczonymi wartościami
        wspolczynniki.clear()
        wspolczynniki.addAll(result)  // Przypisujemy zaktualizowaną listę
    }

    // Operator złożony -= dla wielomianów
    operator fun minusAssign(drugiWielomian: Wielomian) {
        // Wyznaczenie maksymalnej długości spośród obu list współczynników
        val maksimum = maxOf(this.wspolczynniki.size, drugiWielomian.wspolczynniki.size)
        // Utworzenie nowej listy wypelnionej zerami
        val result = MutableList(maksimum) { 0.0 }

        // Dodanie współczynników z bieżącego wielomianu
        for (i in 0 until this.wspolczynniki.size) {
            result[i] += this.wspolczynniki[i]
        }

        // Dodanie współczynników z drugiego wielomianu
        for (i in 0 until drugiWielomian.wspolczynniki.size) {
            result[i] -= drugiWielomian.wspolczynniki[i]
        }

        // Zastąpienie współczynników nowo obliczonymi wartościami
        wspolczynniki.clear()
        wspolczynniki.addAll(result)  // Przypisujemy zaktualizowaną listę
    }
}

/**
 * Funkcje testujace
 */

// Funkcja testująca metodę stopien()
fun testStopien() {
    println("Test metody stopien(): ")
    val w1 = Wielomian(listOf(5.5, 0.0, -3.0, 5.0))
    val w2 = Wielomian(listOf(0.0, 0.0, 0.0))
    println("Oczekiwany stopien: 2, Faktyczny: ${w1.stopien()}")
    println("Oczekiwany stopien: 2, Faktyczny: ${w2.stopien()}")
    println()
}

// Funkcja testująca metodę toStringWielomian()
fun testToStringWielomian() {
    println("Test metody toStringWielomian():")
    val w = Wielomian(listOf(3.5, 0.0, -3.1))
    val oczekiwany = "3.5x^2 - 3.1"
    val wynik = w.toStringWielomian()
    println("Oczekiwana reprezentacja tekstowa: $oczekiwany")
    println("Faktyczna reprezentacja tekstowa: $wynik")
    println()
}

// Funkcja testująca metodę wartoscWielomianu()
fun testWartoscWielomianu() {
    println("Test metody wartoscWielomianu(): ")
    val w = Wielomian(listOf(1.0, -2.0, 1.0))
    val x = 1.0
    val wynik = w.wartoscWielomianu(x)
    println("Dla x = $x, oczekiwana wartość wielomianu to: 0.0, natomiast obliczona to: $wynik")
    println()
}

// Funkcja testująca operator +
fun testDodawanie() {
    println("Test operatora (+): ")
    val w1 = Wielomian(listOf(1.0, 2.0))
    val w2 = Wielomian(listOf(3.0, 4.0, 5.0))
    val wynikDodawania = w1 + w2
    println("Oczekiwany wynik: 4.0x^2 + 6.0x + 5.0" )
    println( "Faktyczny wynik: ${wynikDodawania.toStringWielomian()}")
    println()
}

// Funkcja testująca operator -
fun testOdejmowanie() {
    println("Test operatora (-): ")
    val w1 = Wielomian(listOf(5.0, 0.0, 0.0))
    val w2 = Wielomian(listOf(10.0, 1.0))
    val wynik = w1 - w2
    println("Oczekiwany wynik: -5.0x^2 - 1.0x")
    println("Faktyczny wynik: ${wynik.toStringWielomian()}")
    println()
}

// Funkcja testująca operator *
fun testMnozenie() {
    println("Test operatora (*): ")
    val w1 = Wielomian(listOf(1.0, 1.0))
    val w2 = Wielomian(listOf(1.0, -1.0))

    val wynik = w1 * w2
    println("Oczekiwany wynik: 1.0x^2 - 1.0")
    println("Faktyczny wynik: ${wynik.toStringWielomian()}")
    println()
}

// Funkcja testująca operator +=
fun testPlusAssign() {
    println("Test dla operatora +=")
    val w1 = Wielomian(listOf(1.0, 2.0))
    val w2 = Wielomian(listOf(0.0, 1.0, 2.0, 3.0))
    w1 += w2
    println("Oczekiwany wynik działania: 1.0x^3 + 3.0x^2 + 2.0x + 3.0")
    println("Faktyczny wynik działania: ${w1.toStringWielomian()}")
    println()
}

// Funkcja testująca operator -=
fun testMinusAssign() {
    println("Test dla operatora -=")
    val w1 = Wielomian(listOf(5.0, 1.0, 3.5))
    val w2 = Wielomian(listOf(2.0, 2.0, 4.0, 0.5))
    w1 -= w2
    println("Oczekiwany wynik działania: 3.0x^3 - 1.0x^2 - 0.5x - 0.5")
    println("Faktyczny wynik działania: ${w1.toStringWielomian()}")
    println()
}

fun main() {
    val wielomian = Wielomian(listOf(5.5, 0.0, -3.0, 5.0))

    // Wypisanie stopnia wielomianu
    println("Stopień wielomianu dla podanych współczynników wynosi: ${wielomian.stopien()}")

    // Wypisanie tekstowej reprezentacji wielomianu
    println("Tekstowa reprezentacja wielomianu: W(x) = ${wielomian.toStringWielomian()}")

    val x = 2.0 // Zadana wartość x, dla której będzie obliczana wartość wielomianu

    // Wypisanie obliczonej wartości wielomianu dla x
    println("Wartość wielomianu W($x) = ${wielomian.wartoscWielomianu(x)}")

    //Zdefiniowanie wspolczynnikow wielomianow do wykonania dzialan na operatorach
    val wielomian_1 = Wielomian(listOf(5.5, 0.0, -3.0, 5.0))
    val wielomian_2 = Wielomian(listOf(1.0, -2.0, 3.0))


    // Wypisanie tekstowej reprezentacji tych wielomianow
    println("Pierwszy wielomian: W(x) = ${wielomian_1.toStringWielomian()}")
    println("Drugi wielomian: W(x) = ${wielomian_2.toStringWielomian()}")

    val dodawanie = wielomian_1 + wielomian_2
    println("Wynik dodawania wielomianów: W(x) = ${dodawanie.toStringWielomian()}")

    val odejmowanie = wielomian_1 - wielomian_2
    println("Wynik odejmowania wielomianów: W(x) = ${odejmowanie.toStringWielomian()}")

    val mnozenie = wielomian_1 * wielomian_2
    println("Wynik mnożenia wielomianów: W(x) = ${mnozenie.toStringWielomian()}")

    //Zdefiniowanie wspolczynnikow wielomianu do wykonania dzialan na operatorach zlozonych
    var wielomian_3 = Wielomian(listOf(4.0, 1.0))

    wielomian_3 = wielomian_3 + Wielomian(listOf(2.0, -5.0))
    println("Wynik dodawania: ${wielomian_3.toStringWielomian()}")

    wielomian_3 = wielomian_3 - Wielomian(listOf(1.0, 2.0))
    println("Wynik odejmowania: ${wielomian_3.toStringWielomian()}")
    println()

    testStopien()
    testMnozenie()
    testDodawanie()
    testOdejmowanie()
    testPlusAssign()
    testMinusAssign()
    testToStringWielomian()
    testWartoscWielomianu()
}

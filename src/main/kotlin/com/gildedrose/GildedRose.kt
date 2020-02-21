package com.gildedrose

class GildedRose(var items: Array<Item>) {
    fun updateQuality() {
        items.forEach {it.asAgingItem().age() }
    }
}

sealed class AgingItem(private val item: Item) {
    fun Item.decreaseSellDate() = sellIn--

    open fun age() {
        item.decreaseSellDate()
        item.age()
    }

    open fun Item.age() {}
}

class Sulfuras(item: Item): AgingItem(item) {
    override fun age() {
        // Sulfuras doesn't age!
    }
}

class AgedBrie(item: Item): AgingItem(item) {
    override fun Item.age() {
        when {
            isPastSellingDate() -> increaseQualityBy(2)
            else -> increaseQualityBy(1)
        }
    }
}

class BackstagePass(item: Item): AgingItem(item) {
    fun Item.isToBeSoldInLessThan(days: Int) = sellIn < days
    fun Item.removeAllQuality() {
        quality = 0
    }

    override fun Item.age() {
        when {
            isPastSellingDate() -> removeAllQuality()
            else -> {
                when {
                    isToBeSoldInLessThan(6) -> increaseQualityBy(3)
                    isToBeSoldInLessThan(11) -> increaseQualityBy(2)
                    else -> increaseQualityBy(1)
                }
            }
        }
    }
}

class Conjured(item: Item): AgingItem(item) {
    override fun Item.age() {
        when {
            isPastSellingDate() -> decreaseQualityBy(4)
            else -> decreaseQualityBy(2)
        }
    }
}

class NormalItem(item: Item): AgingItem(item) {
    override fun Item.age() {
        when {
            isPastSellingDate() -> decreaseQualityBy(2)
            else -> decreaseQualityBy(1)
        }
    }
}

fun Item.asAgingItem(): AgingItem {
    fun Item.isSulfuras() = name == "Sulfuras, Hand of Ragnaros"
    fun Item.isConjured() = name == "Conjured Mana Cake"
    fun Item.isBackstagePass() = name == "Backstage passes to a TAFKAL80ETC concert"
    fun Item.isAgedBrie() = name == "Aged Brie"

    return when {
        isSulfuras() -> Sulfuras(this)
        isAgedBrie() -> AgedBrie(this)
        isBackstagePass() -> BackstagePass(this)
        isConjured() -> Conjured(this)
        else -> NormalItem(this)
    }
}

fun Item.decreaseQualityBy(amount: Int) {
    fun Item.hasQualityLeft() = quality > 0

    (1..amount).forEach { _ ->
        if (hasQualityLeft()) quality--
    }
}
fun Item.increaseQualityBy(amount: Int) {
    fun Item.hasReachedMaximumQuality() = quality >= 50

    (1..amount).forEach { _ ->
        if (!hasReachedMaximumQuality()) quality++
    }
}

fun Item.isPastSellingDate() = sellIn < 0

package com.gildedrose

class GildedRose(var items: Array<Item>) {
    fun updateQuality() {
        items.forEach {item ->
            when {
                !item.isSulfuras() -> item.age()
            }
        }
    }

    private fun Item.age() {
        sellIn -= 1
        when {
            isAgedBrie() -> when {
                isPastSellingDate() -> increaseQualityBy(2)
                else -> increaseQualityBy(1)
            }
            isBackstagePass() -> when {
                isPastSellingDate() -> considerAsAPieceOfCrap()
                else -> {
                    when {
                        isToBeSoldInLessThan(6) -> increaseQualityBy(3)
                        isToBeSoldInLessThan(11) -> increaseQualityBy(2)
                        else -> increaseQualityBy(1)
                    }
                }
            }
            isConjured() -> when {
                isPastSellingDate() -> decreaseQualityBy(4)
                else -> decreaseQualityBy(2)
            }
            else -> when {
                isPastSellingDate() -> decreaseQualityBy(2)
                else -> decreaseQualityBy(1)
            }
        }
    }

    private fun Item.decreaseQualityBy(amount: Int) {
        (1..amount).forEach { _ ->
            if (hasQualityLeft()) quality -= 1
        }
    }

    private fun Item.increaseQualityBy(amount: Int) {
        (1..amount).forEach { _ ->
            if (!hasReachedMaximumQuality()) quality += 1
        }
    }

    private fun Item.considerAsAPieceOfCrap() {
        quality = 0
    }

    private fun Item.isToBeSoldInLessThan(days: Int) = sellIn < days

    private fun Item.isPastSellingDate() = sellIn < 0

    private fun Item.hasQualityLeft() = quality > 0

    private fun Item.hasReachedMaximumQuality() = quality >= 50

    private fun Item.isSulfuras() = name == "Sulfuras, Hand of Ragnaros"

    private fun Item.isConjured() = name == "Conjured Mana Cake"

    private fun Item.isBackstagePass() = name == "Backstage passes to a TAFKAL80ETC concert"

    private fun Item.isAgedBrie() = name == "Aged Brie"
}


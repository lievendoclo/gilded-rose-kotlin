package com.gildedrose

import org.junit.Assert.*
import org.junit.Test

class GildedRoseTest {

    @Test fun `Items degrade`() {
        val items = arrayOf<Item>(Item("not that old", 1, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(9, app.items[0].quality)
        assertEquals(0, app.items[0].sellIn)

    }

    @Test fun `Items past sell date degrade twice as fast`() {
        val items = arrayOf<Item>(Item("old", 0, 10))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(8, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }

    @Test fun `Quality can never be negative`() {
        val items = arrayOf<Item>(Item("old", 0, 1))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(0, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }

    @Test fun `Aged Brie gets better the older it gets`() {
        val items = arrayOf<Item>(Item("Aged Brie", 10, 0))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(1, app.items[0].quality)
        assertEquals(9, app.items[0].sellIn)
    }

    @Test fun `Aged Brie never gets better than 50`() {
        val items = arrayOf<Item>(Item("Aged Brie", 10, 50), Item("Aged Brie", 0, 50))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(50, app.items[0].quality)
        assertEquals(9, app.items[0].sellIn)
        assertEquals(50, app.items[1].quality)
        assertEquals(-1, app.items[1].sellIn)
    }

    @Test fun `Backstage passes increase in quality`() {
        val items = arrayOf<Item>(Item("Backstage passes to a TAFKAL80ETC concert", 8, 20))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(22, app.items[0].quality)
        assertEquals(7, app.items[0].sellIn)
        app.updateQuality()
        assertEquals(24, app.items[0].quality)
        assertEquals(6, app.items[0].sellIn)
        app.updateQuality()
        assertEquals(27, app.items[0].quality)
        assertEquals(5, app.items[0].sellIn)
    }

    @Test fun `Backstage passes become worthless if they expire`() {
        val items = arrayOf<Item>(Item("Backstage passes to a TAFKAL80ETC concert", 2, 20))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(23, app.items[0].quality)
        assertEquals(1, app.items[0].sellIn)
        app.updateQuality()
        assertEquals(26, app.items[0].quality)
        assertEquals(0, app.items[0].sellIn)
        app.updateQuality()
        assertEquals(0, app.items[0].quality)
        assertEquals(-1, app.items[0].sellIn)
    }

    @Test fun `Sulfuras never decreases in quality`() {
        val items = arrayOf<Item>(Item("Sulfuras, Hand of Ragnaros", 10, 80), Item("Sulfuras, Hand of Ragnaros", 0, 80))
        val app = GildedRose(items)
        app.updateQuality()
        assertEquals(80, app.items[0].quality)
        assertEquals(10, app.items[0].sellIn)
        assertEquals(80, app.items[1].quality)
        assertEquals(0, app.items[1].sellIn)
    }
}



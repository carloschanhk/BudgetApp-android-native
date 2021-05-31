package com.example.budget.common

import com.example.budget.R

enum class CategoryType(val type: String, val icon: Int, val color: Int) {
    APPARELS("Apparels", R.drawable.ic_casual_t_shirt_24, R.drawable.apparels_progressbar),
    HOUSING(
        "Housing",
        R.drawable.ic_baseline_home_24,
        R.drawable.housing_progressbar
    ),
    SHOPS("Shops", R.drawable.ic_baseline_shopping_cart_24, R.drawable.shops_progressbar),
    FOOD(
        "Food",
        R.drawable.ic_baseline_fastfood_24,
        R.drawable.food_progressbar
    ),
    TRANSIT(
        "Transit",
        R.drawable.ic_baseline_directions_transit_24,
        R.drawable.transit_progressbar
    ),
    LEISURE(
        "Leisure",
        R.drawable.ic_baseline_sports_esports_24,
        R.drawable.leisure_progressbar
    ),
    HEALTH("Health", R.drawable.ic_baseline_favorite_24, R.drawable.health_progressbar)
}
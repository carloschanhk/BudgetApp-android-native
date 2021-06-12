package com.example.budget.common

import com.example.budget.R

enum class CategoryType(val type: String, val icon: Int, val color: Int) {
    APPARELS("Apparels", R.drawable.ic_casual_t_shirt_24, R.drawable.progressbar_apparels),
    HOUSING(
        "Housing",
        R.drawable.ic_baseline_home_24,
        R.drawable.progressbar_housing
    ),
    SHOPPING("Shopping", R.drawable.ic_baseline_shopping_cart_24, R.drawable.progressbar_shops),
    FOOD(
        "Food",
        R.drawable.ic_baseline_fastfood_24,
        R.drawable.progressbar_food
    ),
    TRANSIT(
        "Transit",
        R.drawable.ic_baseline_directions_transit_24,
        R.drawable.progressbar_transit
    ),
    LEISURE(
        "Leisure",
        R.drawable.ic_baseline_sports_esports_24,
        R.drawable.progressbar_leisure
    ),
    HEALTH("Health", R.drawable.ic_baseline_favorite_24, R.drawable.progressbar_health)
}
package com.example.mypet.domain.pet.kind

import com.example.mypet.app.R


fun getKindIconResId(petKindOrdinal: Int) =
    PetKind.values()[petKindOrdinal].iconResId
fun getKindNameResId(petKindOrdinal: Int) =
    PetKind.values()[petKindOrdinal].nameResId

enum class PetKind(val iconResId: Int, val nameResId: Int,) {
    CAT(R.drawable.ic_pet_kind_cat, R.string.pet_kind_cat),
    DOG(R.drawable.ic_pet_kind_dog, R.string.pet_kind_dog),
    RABBIT(R.drawable.ic_pet_kind_rabbit, R.string.pet_kind_rabbit),
    PARROT(R.drawable.ic_pet_kind_parrot, R.string.pet_kind_parrot),
    HAMSTER(R.drawable.ic_pet_kind_hamster, R.string.pet_kind_hamster),
    FISH(R.drawable.ic_pet_kind_fish, R.string.pet_kind_fish),
    TURTLE(R.drawable.ic_pet_kind_turtle, R.string.pet_kind_turtle),
    GUINEA_PIG(R.drawable.ic_pet_kind_guinea_pig, R.string.pet_kind_guinea_pig),
    RAT(R.drawable.ic_pet_kind_rat, R.string.pet_kind_rat),
    FERRET(R.drawable.ic_pet_kind_ferret, R.string.pet_kind_ferret),
    CANARY(R.drawable.ic_pet_kind_canary, R.string.pet_kind_canary),
    CHINCHILLA(R.drawable.ic_pet_kind_chinchilla, R.string.pet_kind_chinchilla),
    CHAMELEON(R.drawable.ic_pet_kind_chameleon, R.string.pet_kind_chameleon),
    CROCODILE(R.drawable.ic_pet_kind_crocodile, R.string.pet_kind_crocodile),
    SNAKE(R.drawable.ic_pet_kind_snake, R.string.pet_kind_snake),
    RACOON(R.drawable.ic_pet_kind_raccoon, R.string.pet_kind_racoon),
    FOX(R.drawable.ic_pet_kind_fox, R.string.pet_kind_fox),
    SPIDER(R.drawable.ic_pet_kind_spider, R.string.pet_kind_spider),
    MADAGASCAR_HISSING_COCKROACH(R.drawable.ic_pet_kind_madagascar_hissing_cockroach, R.string.pet_kind_madagascar_hissing_cockroach),
    SNAIL(R.drawable.ic_pet_kind_snail, R.string.pet_kind_snail),
    MINI_PIG(R.drawable.ic_pet_kind_mini_pig, R.string.pet_kind_mini_pig),
    OWL(R.drawable.ic_pet_kind_owl, R.string.pet_kind_owl),
    MONKEY(R.drawable.ic_pet_kind_monkey, R.string.pet_kind_monkey),
    FROG(R.drawable.ic_pet_kind_frog, R.string.pet_kind_frog),
    HEDGEHOG(R.drawable.ic_pet_kind_hedgehog, R.string.pet_kind_hedgehog),
    HORSE(R.drawable.ic_pet_kind_horse, R.string.pet_kind_horse),
}
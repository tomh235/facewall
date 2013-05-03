package repository

import domain.Person

class KittenRepo {

    def getKittens: List[Person] = {
        (1 until 10).map { kitten =>
            Person("http://placekitten.com/200/30" + kitten, "Kitten" + kitten)
        }.toList
    }

}

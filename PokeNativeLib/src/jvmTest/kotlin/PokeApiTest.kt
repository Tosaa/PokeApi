import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PokeApiTest {

    private fun readDittoJson(): String? {
        return PokeApiTest::class.java.getResource("/ditto.json")?.readText()
    }

    private fun readPikachuJson(): String? {
        return PokeApiTest::class.java.getResource("/pikachu.json")?.readText()
    }

    private val api = PokeApi()
    private val curlExecutor = object : CurlExecutor() {
        override fun invoke(request: String): String? {
            return when {
                request.contains("ditto") -> readDittoJson()
                request.contains("pikachu") -> readPikachuJson()
                else -> {
                    println("no pokemon in request: $request")
                    null
                }
            }
        }
    }

    init {
        api.setCurlExecutor(curlExecutor)
    }


    @Test
    fun testSetup() {
        readDittoJson().let {
            assertNotNull(it)
            assertTrue { it.contains("ditto") }
        }

        assertNotNull(readPikachuJson()).let {
            assertNotNull(it)
            assertTrue { it.contains("pikachu") }
        }
    }

    @Test
    fun testDitto() {
        val pokemonName = "ditto"
        api.findPokemon(pokemonName).let {
            assertNotNull(it, "Pokemon should not be 'null'")
            assertEquals(pokemonName, it.name, "Name is not correct")
        }
    }

    @Test
    fun testPikachu() {
        val pokemonName = "pikachu"
        api.findPokemon(pokemonName).let {
            assertNotNull(it, "Pokemon should not be 'null'")
            assertEquals(pokemonName, it.name, "Name is not correct")
        }
    }
}

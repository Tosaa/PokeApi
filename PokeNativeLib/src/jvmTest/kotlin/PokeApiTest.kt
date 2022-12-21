import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import redtoss.poke.lib.CurlExecutor
import redtoss.poke.lib.Logger
import redtoss.poke.lib.PokeApi
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class PokeApiTest {

    init {
        Logger.loggingFunction = { println("\t$it") }
    }

    private fun readDittoJson(): String? {
        return PokeApiTest::class.java.getResource("/ditto.json")?.readText()
    }

    private fun readPikachuJson(): String? {
        return PokeApiTest::class.java.getResource("/pikachu.json")?.readText()
    }

    private val api = PokeApi()
    private val curlExecutor = object : CurlExecutor() {
        override suspend fun invoke(request: String): String? {
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
        println("Test: testSetup")
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
        println("Test: testDitto")
        val pokemonName = "ditto"
        CoroutineScope(Dispatchers.IO).launch {
            api.findPokemon(pokemonName).let {
                assertNotNull(it, "Pokemon should not be 'null'")
                assertEquals(pokemonName, it.name, "Name is not correct")
                println(it.toString())
            }
        }

    }

    @Test
    fun testPikachu() {
        println("Test: testPikachu")
        val pokemonName = "pikachu"
        CoroutineScope(Dispatchers.IO).launch {
            api.findPokemon(pokemonName).let {
                assertNotNull(it, "Pokemon should not be 'null'")
                assertEquals(pokemonName, it.name, "Name is not correct")
                println(it.toString())
            }
        }
    }

    @AfterTest
    fun afterTests() {
        println()
    }
}

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import redtoss.poke.lib.logging.Logger
import redtoss.poke.lib.PokeApiClient
import kotlin.test.*

class PokeApiTest {

    val logger = Logger()

    private fun readDittoJson(): String? {
        return PokeApiTest::class.java.getResource("/ditto.json")?.readText()
    }

    private fun readPikachuJson(): String? {
        return PokeApiTest::class.java.getResource("/pikachu.json")?.readText()
    }

    @Test
    fun testJsonExists() {
        runBlocking(Dispatchers.IO) {
            println("Test: testJsonExists")
            readDittoJson().let {
                assertNotNull(it)
                assertTrue { it.contains("ditto") }
            }

            assertNotNull(readPikachuJson()).let {
                assertNotNull(it)
                assertTrue { it.contains("pikachu") }
            }
        }
    }

    @Test
    fun testDitto() {
        val api = PokeApiClient(logger = logger)
        println("Test: testDitto")
        val pokemonName = "ditto"
        runBlocking(Dispatchers.IO) {
            api.findPokemon(pokemonName).let {
                assertNotNull(it, "Pokemon should not be 'null'")
                assertEquals(pokemonName, it.name, "Name is not correct")
                println(it.toString())
            }
        }

    }

    @Test
    fun testPikachu() {
        val api = PokeApiClient(logger = logger)
        println("Test: testPikachu")
        val pokemonName = "pikachu"
        runBlocking(Dispatchers.IO) {
            api.findPokemon(pokemonName).let {
                assertNotNull(it, "Pokemon should not be 'null'")
                assertEquals(pokemonName, it.name, "Name is not correct")
                println(it.toString())
            }
        }
    }

    @Test
    fun testCache() {
        val countingCache = TestCustomCache()
        val api = PokeApiClient(logger = logger, cache = countingCache)
        println("Test: testCache")
        val pokemonName = "pikachu"
        runBlocking(Dispatchers.IO) {
            assert(countingCache.addPokemonCounter == 0) { "Cache.addPokemon should not have been called yet" }
            api.findPokemon(pokemonName).let {
                assert(countingCache.addPokemonCounter == 1) { "Cache.addPokemon should have been called exact once now" }
                assert(countingCache.getPokemonByURLCounter == 1) { "Cache.getPokemonByURL should have been called exact once now" }
                assertNotNull(it, "Pokemon should not be 'null'")
                assertEquals(pokemonName, it.name, "Name is not correct")
                println(it.toString())
            }
            api.findPokemon(pokemonName).let {
                assert(countingCache.addPokemonCounter == 1) { "Cache.addPokemon should not have been called again" }
                assert(countingCache.getPokemonByURLCounter == 2) { "Cache.getPokemonByURL should have been called exact twice now" }
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

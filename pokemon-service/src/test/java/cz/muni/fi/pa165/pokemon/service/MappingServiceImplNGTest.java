package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dto.PokemonDTO;
import cz.muni.fi.pa165.pokemon.entity.Pokemon;
import cz.muni.fi.pa165.pokemon.entity.Trainer;
import cz.muni.fi.pa165.pokemon.enums.PokemonType;
import java.sql.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import org.dozer.Mapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test of proper functioning of mapping service
 *
 * @author Jaroslav Cechak
 */
@ContextConfiguration(classes = {cz.muni.fi.pa165.pokemon.context.ServiceConfiguration.class})
public class MappingServiceImplNGTest extends AbstractTestNGSpringContextTests {

    @InjectMocks
    @Inject
    private MappingServiceImpl mappingService;

    @Mock
    private Mapper mapper;

    private static Set<Pokemon> PokemonSet;
    private static List<Pokemon> PokemonList;
    private static Trainer trainer;
    private static Pokemon pokemon;
    private static Pokemon pokemon2;
    private static List<PokemonDTO> PokemonDTOList;
    private static PokemonDTO pokemonDTO;
    private static PokemonDTO pokemonDTO2;
    
    public MappingServiceImplNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
        PokemonSet = new HashSet<>();
        PokemonList = new LinkedList<>();
        trainer = new Trainer();
        pokemon = new Pokemon();
        pokemon2 = new Pokemon();
        PokemonDTOList = new LinkedList<>();
        pokemonDTO = new PokemonDTO();
        pokemonDTO2 = new PokemonDTO();

        trainer = new Trainer();
        trainer.setId(1l);
        trainer.setName("Ask");
        trainer.setSurname("Ketchum");
        trainer.setDateOfBirth(new Date(System.currentTimeMillis()));    

        pokemon.setId(20l);
        pokemon.setName("Pika");
        pokemon.setNickname("Chu");
        pokemon.setSkillLevel(10);
        pokemon.setTrainer(trainer);
        pokemon.setType(PokemonType.ROCK);

        pokemon2.setId(2l);
        pokemon2.setName("Bulbasaur");
        pokemon2.setNickname("B");
        pokemon2.setSkillLevel(20);
        pokemon2.setTrainer(trainer);
        pokemon2.setType(PokemonType.GRASS);

        pokemonDTO.setId(pokemon.getId());
        pokemonDTO.setName(pokemon.getName());
        pokemonDTO.setNickname(pokemon.getNickname());
        pokemonDTO.setSkillLevel(pokemon.getSkillLevel());
        pokemonDTO.setTrainerId(pokemon.getTrainer().getId());
        pokemonDTO.setType(pokemon.getType());

        pokemonDTO2.setId(pokemon2.getId());
        pokemonDTO2.setName(pokemon2.getName());
        pokemonDTO2.setNickname(pokemon2.getNickname());
        pokemonDTO2.setSkillLevel(pokemon2.getSkillLevel());
        pokemonDTO2.setTrainerId(pokemon2.getTrainer().getId());
        pokemonDTO2.setType(pokemon2.getType());

        PokemonSet.add(pokemon);
        PokemonSet.add(pokemon2);

        PokemonList.add(pokemon2);
        PokemonList.add(pokemon);

        PokemonDTOList.add(pokemonDTO2);
        PokemonDTOList.add(pokemonDTO);
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
        MockitoAnnotations.initMocks(this);
        
        when(mapper.map(pokemon, PokemonDTO.class)).thenReturn(pokemonDTO);
        when(mapper.map(pokemon2, PokemonDTO.class)).thenReturn(pokemonDTO2);
        when(mapper.map(pokemonDTO2, Pokemon.class)).thenReturn(pokemon2);
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    @Test
    public void testInjection() {
        assertNotNull(mappingService, "Service has not been injeted. Check annotations.");
    }

    /**
     * Test of map method, of class MappingServiceImpl.
     */
    @Test
    public void testMap_Collection_Class() {
        List<PokemonDTO> result = mappingService.map(PokemonSet, PokemonDTO.class);

        assertTrue(result.size() == 2, "Mapped set does not contains all items.");
        assertTrue(result.containsAll(PokemonDTOList), "Mapped set does not conaint expected items.");

        result = mappingService.map(PokemonList, PokemonDTO.class);
        assertTrue(result.size() == 2, "Mapped list does not contains all items.");
        assertTrue(result.containsAll(PokemonDTOList), "Mapped list does not conaint expected items.");
        assertEquals(result, PokemonDTOList, "Mapped list does not contain objects in the same order.");
    }

    /**
     * Test of map method, of class MappingServiceImpl.
     */
    @Test
    public void testMap_Object_Class() {
        PokemonDTO result = mappingService.map(pokemon, PokemonDTO.class);

        assertEquals(result, pokemonDTO, "Object has not been mapped correctly.");

        Pokemon result2 = mappingService.map(pokemonDTO2, Pokemon.class);
        
        assertEquals(result2, pokemon2, "Object has not been mapped correctly");
    }

    /**
     * Test of getMapper method, of class MappingServiceImpl.
     */
    @Test
    public void testGetMapper() {
        assertNotNull(mappingService.getMapper(), "Returned mapper is null.");
        assertSame(mappingService.getMapper(), mapper, "Returned mapper is not the same as the one in the context.");
    }

}
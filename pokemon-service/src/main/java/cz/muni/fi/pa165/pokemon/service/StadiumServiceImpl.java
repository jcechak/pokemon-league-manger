package cz.muni.fi.pa165.pokemon.service;

import cz.muni.fi.pa165.pokemon.dao.StadiumDao;
import cz.muni.fi.pa165.pokemon.entity.Stadium;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * @author Dominika Talianova
 */
@Service
public class StadiumServiceImpl implements StadiumService {

    @Inject
    private StadiumDao stadiumDao;

    @Override
    public void createStadium(Stadium stadium){
        stadiumDao.create(stadium);
    }

    @Override
    public void updateStadium(Stadium stadium){
        stadiumDao.update(stadium);
    }

    @Override
    public void deleteStadium(Stadium stadium){
        stadiumDao.delete(stadium);
    }

    @Override
    public Stadium getStadiumById(Long id){
        return (stadiumDao.findById(id));

    }

    @Override
    public List<Stadium> getAll(){
        return (stadiumDao.findAll());
    }


}

package fr.epita.actionlearning.repositories;

import fr.epita.actionlearning.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address,Integer> {
    @Query("SELECT a from Address a WHERE lower(a.area) = lower(?1) AND " +
            "lower(a.city)=lower(?2) AND lower(a.country)=lower(?3) AND lower(a.number) = lower(?4) " +
            "AND lower(a.street)=lower(?5) ")
    List<Address> searchAddress(String area, String city,String country,String number,String street);
}

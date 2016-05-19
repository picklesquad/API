package com.pickle.Service;

import com.pickle.Domain.BanksampahEntity;
import org.hibernate.mapping.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by andrikurniawan.id@gmail.com on 5/16/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class BankServiceTest {

    @Autowired
    @Mock
    private BankService bankService;

    @Mock
    BanksampahEntity banksampahEntity;

    @Test
    public void validation() throws Exception {
        when(bankService.validation("081245692312",
                "syukrimullia")).thenReturn(banksampahEntity);
    }

    @Test
    public void findById() throws Exception {
        when(bankService.findById(1)).thenReturn(banksampahEntity);
    }

    @Test
    public void searchByLocation() throws Exception {
//        when(bankService.searchByLocation("")).thenReturn(List<banksampahEntity>);
    }

    @Test
    public void save() throws Exception {

    }

}
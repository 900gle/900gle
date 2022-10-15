//package com.bbongdoo.doo.service;
//
//import com.bbongdoo.doo.domain.Keywords;
//import com.bbongdoo.doo.domain.KeywordsRepository;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.Optional;
//
//@SpringBootTest
//public class KeywordServiceTest {
//
//    @Autowired
//    KeywordsRepository keywordsRepository;
//
//    @Test
//    @Transactional
//    public void getKeywords(){
//
//
//
//        Optional<List<Keywords>> list = Optional.of(keywordsRepository.findAllByUseYn("Y"));
//
//        if(list.isPresent()){
//
//            System.out.println("aaa");
//        }
//
////       List<Keywords> list =  keywordsRepository.findAllByUseYn("Y");
////       list.stream().forEach(x-> System.out.println(x.));
//    }
//
//
//}

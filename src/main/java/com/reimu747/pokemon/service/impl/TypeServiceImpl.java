package com.reimu747.pokemon.service.impl;

import com.reimu747.pokemon.dao.TypeDao;
import com.reimu747.pokemon.model.vo.TypeVO;
import com.reimu747.pokemon.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @ClassName TypeServiceImpl
 * @Author Reimu747
 * @Date 2019/1/5 6:17
 * @Description
 * @Version 1.0
 **/
@Service
public class TypeServiceImpl implements TypeService
{
    @Autowired
    private TypeDao typeDao;

    private static final String TYPE_TABLE_SEPARATOR = ",";

    @Override
    public void printTypeTable()
    {
        List<TypeVO> list = typeDao.getAllTypes();
        for (TypeVO typeVO : list)
        {
            System.out.print("" + typeVO.getName());
        }
        System.out.println();

        for (TypeVO typeVO : list)
        {
            String typeName = typeVO.getName();
            String[] typeRelation = new String[list.size()];

            Optional<String> notEffectiveOptional = Optional.ofNullable(typeVO.getNotEffective());
            notEffectiveOptional.ifPresent(s ->
            {
                typeRelationStringHandle(s, typeRelation, "0x");
            });

            Optional<String> notVeryEffectiveOptional = Optional.ofNullable(typeVO.getNotVeryEffective());
            notVeryEffectiveOptional.ifPresent(s ->
            {
                typeRelationStringHandle(s, typeRelation, "1/2x");
            });

            Optional<String> superEffectiveOptional = Optional.ofNullable(typeVO.getSuperEffective());
            superEffectiveOptional.ifPresent(s ->
            {
                typeRelationStringHandle(s, typeRelation, "2x");
            });

            for (int i = 0; i < typeRelation.length; i++)
            {
                if (typeRelation[i] == null)
                {
                    typeRelation[i] = "1x";
                }
            }

            StringBuilder format = new StringBuilder(typeName);
            for (int i = 0; i < typeRelation.length; i++)
            {
                format.append("\t\t" + typeRelation[i]);
            }
            System.out.println(format);
        }
    }

    private void typeRelationStringHandle(String s, String[] strArray, String newStr)
    {
        String[] array = s.split(TYPE_TABLE_SEPARATOR);
        List<String> list = Arrays.asList(array);
        for (int i = 0; i < strArray.length; i++)
        {
            if (list.contains(String.valueOf(i + 1)))
            {
                strArray[i] = newStr;
            }
        }
    }
}

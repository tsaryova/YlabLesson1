package io.ylab.lessons.lesson3.orgstructure;

import java.io.File;
import java.io.IOException;

public interface OrgStructureParser {
    /**
     * считывает данные из файла csv и возвращать ссылку на сотрудника, атрибут boss_id которого не задан
     *
     * @param csvFile
     * @return
     * @throws IOException
     */
    public Employee parseStructure(File csvFile) throws IOException;
}

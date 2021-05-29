package jdbc.service.converters;

import jdbc.dao.entity.SkillDao;
import jdbc.dto.SkillTo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class SkillConverter {
    public static SkillDao toSkillDao(SkillTo skillTo) {
        SkillDao skillDao = new SkillDao();
        skillDao.setIdSkill(skillTo.getIdSkill());
        skillDao.setLanguage(skillTo.getLanguage());
        skillDao.setLevel(skillTo.getLevel());
        return skillDao;
    }

    public static SkillTo fromSkillDao(SkillDao skillDao) {
        SkillTo skillTo = new SkillTo();
        skillTo.setIdSkill(skillDao.getIdSkill());
        skillTo.setLanguage(skillDao.getLanguage());
        skillTo.setLevel(skillDao.getLevel());
        return skillTo;
    }

    public static SkillDao toSkillDao(ResultSet resultSet) throws SQLException {
        SkillDao skillDao = new SkillDao();
        skillDao.setIdSkill(resultSet.getInt("id_skill"));
        skillDao.setLanguage(resultSet.getString("language"));
        skillDao.setLevel(resultSet.getString("level"));
        return skillDao;
    }

    public static List<SkillTo> allFromSkillDao(List<SkillDao> skillDaos) {
        return skillDaos.stream()
                .map(SkillConverter::fromSkillDao)
                .collect(Collectors.toList());
    }
}

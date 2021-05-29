package jdbc.dto;

import java.util.Objects;

public class SkillTo {
    private int idSkill;
    private String language;
    private String level;

    public int getIdSkill() {
        return idSkill;
    }

    public void setIdSkill(int idSkill) {
        this.idSkill = idSkill;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "SkillTo{" +
                "idSkill=" + idSkill +
                ", language='" + language + '\'' +
                ", level='" + level + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SkillTo skillTo = (SkillTo) o;
        return Objects.equals(language, skillTo.language) && Objects.equals(level, skillTo.level);
    }

    @Override
    public int hashCode() {
        return Objects.hash(language, level);
    }
}

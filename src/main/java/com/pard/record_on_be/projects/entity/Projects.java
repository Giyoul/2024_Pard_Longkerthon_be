package com.pard.record_on_be.projects.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pard.record_on_be.competency_tag.entity.CompetencyTag;
import com.pard.record_on_be.experiences.entity.Experiences;
import com.pard.record_on_be.project_data.entity.ProjectData;
import com.pard.record_on_be.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "projects")
//@ToString(exclude = {"user", "experiencesList", "projectDataList", "competencyTagList"})
public class Projects {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, columnDefinition = "INT")
    private Integer id;

    @Column(name = "user_id", nullable = false, updatable = false)
    private UUID user_id;

    @Column(name = "name", nullable = false, columnDefinition = "TINYTEXT")
    private String name;

    @Column(name = "start_date", nullable = false)
    private Date start_date;

    @Column(name = "update_date", nullable = false)
    private Date update_date;

    @Column(name = "finish_date")
    private Date finish_date;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "picture", columnDefinition = "TEXT")
    private String picture;

    @Column(name = "is_finished", columnDefinition = "TINYINT")
    private Integer is_finished;

    @Column(name = "part", columnDefinition = "TEXT")
    private String part;

    @ManyToOne
    @JoinColumn(name = "user", referencedColumnName = "id")
    @JsonIgnore // 순환참조 방지
    private User user;

    @OneToMany(mappedBy = "projects", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // 순환 참조 방지
    private List<Experiences> experiencesList;

    @OneToMany(mappedBy = "projects", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore // 순환 참조 방지
    private List<ProjectData> projectDataList;

    @OneToMany(mappedBy = "projects", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonIgnore // 순환 참조 방지
    private List<CompetencyTag> competencyTagList;

    public void addPicture(String picture) {
        this.picture = picture;
    }

    public void addExperience(Experiences experience) {
        experiencesList.add(experience);
        experience.setProjects(this);
    }

    public void addProjectData(ProjectData projectData) {
        projectDataList.add(projectData);
        projectData.setProjects(this);
    }

    public void finishProject() {
//        finish_date = new Date();
//        finish_date.setTime(finish_date.getTime() + 24 * 60 * 60 * 1000);
        is_finished = 1;
    }

    public void resumeProject(){
        is_finished = 0;
    }

    public void addCompetencyTag(CompetencyTag competencyTag) {
        competencyTagList.add(competencyTag);
        competencyTag.setProjects(this);
    }
}

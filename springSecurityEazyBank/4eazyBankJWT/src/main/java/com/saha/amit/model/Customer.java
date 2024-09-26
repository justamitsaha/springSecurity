    package com.saha.amit.model;

    import com.fasterxml.jackson.annotation.JsonIgnore;
    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.Getter;
    import lombok.Setter;
    import org.hibernate.annotations.GenericGenerator;

    import jakarta.persistence.*;

    import java.util.Date;
    import java.util.Set;

    @Entity
    @Getter
    @Setter
    public class Customer {

        @Id
        @GeneratedValue(strategy= GenerationType.AUTO,generator="native")
        @GenericGenerator(name = "native",strategy = "native")
        @Column(name = "customer_id")
        private int id;

        private String name;

        private String email;

        @Column(name = "mobile_number")
        private String mobileNumber;

        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        private String pwd;

        private String role;

        @Column(name = "create_dt")
        @JsonIgnore
        private Date createDt;

        @JsonIgnore
        @OneToMany(mappedBy="customer",fetch=FetchType.EAGER)
        private Set<Authority> authorities;

    }

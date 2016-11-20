package wastelander.model.common;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * Created by john on 11/5/2016.
 *
 * @author andrei.belov aka john
 * @link http://vk.com/andrei.belov
 */
@Data
@Entity
@Accessors(chain = true)
public class User implements Serializable {

    // Constants -----------------------------------------------------------------

    private static final long serialVersionUID = 1L;

    // Constructor ---------------------------------------------------------------

    public User(){}

    // Properties ----------------------------------------------------------------

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    @Column(unique=true, nullable=false, columnDefinition = "VARCHAR (32)")
    private String email;
    @Column(unique=true, nullable=false, columnDefinition = "VARCHAR (16)")
    private String login;
    @Column(unique=true, nullable=false, columnDefinition = "VARCHAR (32)")
    private String password;
    private Integer accessLevel = 3;
    @Column(columnDefinition = "VARCHAR (32)")
    private String firstName;
    @Column(columnDefinition = "VARCHAR (32)")
    private String lastName;

    private Date birthDate;
    @Enumerated(EnumType.STRING) @Column(columnDefinition = "VARCHAR (32) default 'UNDEFINED'")
    private Gender sex = Gender.UNDEFINED;

    public enum Gender {

        UNDEFINED("Not selected"),
        MALE("Male"),
        FEMALE("Female"),
        OTHER("Other");

        private final String sex;
        Gender(String gender){
            this.sex = gender;
        }
        public String getSex(){
            return sex;
        }
        public final int getOrdinal() {
            return this.ordinal();
        }
    }
    @Enumerated(EnumType.STRING) @Column(columnDefinition = "VARCHAR (2) default 'RU'")
    private Countries country;

    public enum Countries {
        UN("(please select a country)"),
        RU("Russian Federation"),
        BY("Belarus"),
        KZ("Kazakhstan"),
        UA("Ukraine"),
        US("United States"),
        AF("Afghanistan"),
        AL("Albania"),
        DZ("Algeria"),
        AS("American Samoa"),
        AD("Andorra"),
        AO("Angola"),
        AI("Anguilla"),
        AQ("Antarctica"),
        AG("Antigua and Barbuda"),
        AR("Argentina"),
        AM("Armenia"),
        AW("Aruba"),
        AU("Australia"),
        AT("Austria"),
        AZ("Azerbaijan"),
        BS("Bahamas"),
        BH("Bahrain"),
        BD("Bangladesh"),
        BB("Barbados"),
        BE("Belgium"),
        BZ("Belize"),
        BJ("Benin"),
        BM("Bermuda"),
        BT("Bhutan"),
        BO("Bolivia"),
        BA("Bosnia and Herzegowina"),
        BW("Botswana"),
        BV("Bouvet Island"),
        BR("Brazil"),
        IO("British Indian Ocean Territory"),
        BN("Brunei Darussalam"),
        BG("Bulgaria"),
        BF("Burkina Faso"),
        BI("Burundi"),
        KH("Cambodia"),
        CM("Cameroon"),
        CA("Canada"),
        CV("Cape Verde"),
        KY("Cayman Islands"),
        CF("Central African Republic"),
        TD("Chad"),
        CL("Chile"),
        CN("China"),
        CX("Christmas Island"),
        CC("Cocos (Keeling) Islands"),
        CO("Colombia"),
        KM("Comoros"),
        CG("Congo"),
        CD("Congo, the Democratic Republic of the"),
        CK("Cook Islands"),
        CR("Costa Rica"),
        CI("Cote d'Ivoire"),
        HR("Croatia (Hrvatska)"),
        CU("Cuba"),
        CY("Cyprus"),
        CZ("Czech Republic"),
        DK("Denmark"),
        DJ("Djibouti"),
        DM("Dominica"),
        DO("Dominican Republic"),
        TP("East Timor"),
        EC("Ecuador"),
        EG("Egypt"),
        SV("El Salvador"),
        GQ("Equatorial Guinea"),
        ER("Eritrea"),
        EE("Estonia"),
        ET("Ethiopia"),
        FK("Falkland Islands (Malvinas)"),
        FO("Faroe Islands"),
        FJ("Fiji"),
        FI("Finland"),
        FR("France"),
        FX("France, Metropolitan"),
        GF("French Guiana"),
        PF("French Polynesia"),
        TF("French Southern Territories"),
        GA("Gabon"),
        GM("Gambia"),
        GE("Georgia"),
        DE("Germany"),
        GH("Ghana"),
        GI("Gibraltar"),
        GR("Greece"),
        GL("Greenland"),
        GD("Grenada"),
        GP("Guadeloupe"),
        GU("Guam"),
        GT("Guatemala"),
        GN("Guinea"),
        GW("Guinea-Bissau"),
        GY("Guyana"),
        HT("Haiti"),
        HM("Heard and Mc Donald Islands"),
        VA("Holy See (Vatican City State)"),
        HN("Honduras"),
        HK("Hong Kong"),
        HU("Hungary"),
        IS("Iceland"),
        IN("India"),
        ID("Indonesia"),
        IR("Iran (Islamic Republic of)"),
        IQ("Iraq"),
        IE("Ireland"),
        IL("Israel"),
        IT("Italy"),
        JM("Jamaica"),
        JP("Japan"),
        JO("Jordan"),
        KE("Kenya"),
        KI("Kiribati"),
        KP("Korea, Democratic People's Republic of"),
        KR("Korea, Republic of"),
        KW("Kuwait"),
        KG("Kyrgyzstan"),
        LA("Lao People's Democratic Republic"),
        LV("Latvia"),
        LB("Lebanon"),
        LS("Lesotho"),
        LR("Liberia"),
        LY("Libyan Arab Jamahiriya"),
        LI("Liechtenstein"),
        LT("Lithuania"),
        LU("Luxembourg"),
        MO("Macau"),
        MK("Macedonia, The Former Yugoslav Republic of"),
        MG("Madagascar"),
        MW("Malawi"),
        MY("Malaysia"),
        MV("Maldives"),
        ML("Mali"),
        MT("Malta"),
        MH("Marshall Islands"),
        MQ("Martinique"),
        MR("Mauritania"),
        MU("Mauritius"),
        YT("Mayotte"),
        MX("Mexico"),
        FM("Micronesia, Federated States of"),
        MD("Moldova, Republic of"),
        MC("Monaco"),
        MN("Mongolia"),
        MS("Montserrat"),
        MA("Morocco"),
        MZ("Mozambique"),
        MM("Myanmar"),
        NA("Namibia"),
        NR("Nauru"),
        NP("Nepal"),
        NL("Netherlands"),
        AN("Netherlands Antilles"),
        NC("New Caledonia"),
        NZ("New Zealand"),
        NI("Nicaragua"),
        NE("Niger"),
        NG("Nigeria"),
        NU("Niue"),
        NF("Norfolk Island"),
        MP("Northern Mariana Islands"),
        NO("Norway"),
        OM("Oman"),
        PK("Pakistan"),
        PW("Palau"),
        PA("Panama"),
        PG("Papua New Guinea"),
        PY("Paraguay"),
        PE("Peru"),
        PH("Philippines"),
        PN("Pitcairn"),
        PL("Poland"),
        PT("Portugal"),
        PR("Puerto Rico"),
        QA("Qatar"),
        RE("Reunion"),
        RO("Romania"),
        RW("Rwanda"),
        KN("Saint Kitts and Nevis"),
        LC("Saint LUCIA"),
        VC("Saint Vincent and the Grenadines"),
        WS("Samoa"),
        SM("San Marino"),
        ST("Sao Tome and Principe"),
        SA("Saudi Arabia"),
        SN("Senegal"),
        SC("Seychelles"),
        SL("Sierra Leone"),
        SG("Singapore"),
        SK("Slovakia (Slovak Republic)"),
        SI("Slovenia"),
        SB("Solomon Islands"),
        SO("Somalia"),
        ZA("South Africa"),
        GS("South Georgia and the South Sandwich Islands"),
        ES("Spain"),
        LK("Sri Lanka"),
        SH("St. Helena"),
        PM("St. Pierre and Miquelon"),
        SD("Sudan"),
        SR("Suriname"),
        SJ("Svalbard and Jan Mayen Islands"),
        SZ("Swaziland"),
        SE("Sweden"),
        CH("Switzerland"),
        SY("Syrian Arab Republic"),
        TW("Taiwan, Province of China"),
        TJ("Tajikistan"),
        TZ("Tanzania, United Republic of"),
        TH("Thailand"),
        TG("Togo"),
        TK("Tokelau"),
        TO("Tonga"),
        TT("Trinidad and Tobago"),
        TN("Tunisia"),
        TR("Turkey"),
        TM("Turkmenistan"),
        TC("Turks and Caicos Islands"),
        TV("Tuvalu"),
        UG("Uganda"),
        AE("United Arab Emirates"),
        GB("United Kingdom"),
        UM("United States Minor Outlying Islands"),
        UY("Uruguay"),
        UZ("Uzbekistan"),
        VU("Vanuatu"),
        VE("Venezuela"),
        VN("Viet Nam"),
        VG("Virgin Islands (British)"),
        VI("Virgin Islands (U.S.)"),
        WF("Wallis and Futuna Islands"),
        EH("Western Sahara"),
        YE("Yemen"),
        YU("Yugoslavia"),
        ZM("Zambia"),
        ZW("Zimbabwe");

        private final String name;

        Countries(String country){
            this.name = country;
        }

        public String getName(){
            return  name;
        }


    }

    @Column(columnDefinition = "VARCHAR (27)")
    private String city;

    private Date regDate;
    @Column(columnDefinition = "VARCHAR (16)")
    private String phone;
    @Column(columnDefinition = "VARCHAR (64)")
    private String status;
    private UUID photoUuid;

    // Object overrides -----------------------------------------------------------

    /**
     * Returns the String representation of this User.
     * Not required, it just pleases reading logs.
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return String.format("User[id=%d,email=%s,login=%s]",
                id, email, login);
    }
}

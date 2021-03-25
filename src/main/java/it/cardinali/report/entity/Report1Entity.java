package it.cardinali.report.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/************************************************************************
 * Created: 24/03/2021                                                  *
 * Author:  mcardinali                                                  *
 ************************************************************************/
@Data
@NoArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "test")
public class Report1Entity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  private String name;
  private BigDecimal somma;
  private BigDecimal cdip;
  private BigDecimal caz;
  private BigInteger gg;
  private String cdc;
  private BigInteger imp;
  private String nomefile;

  private Date dataIni;
  private Date dataFin;
  private BigInteger giorni;

}

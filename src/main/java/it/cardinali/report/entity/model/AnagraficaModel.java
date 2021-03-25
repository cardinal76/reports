package it.cardinali.report.entity.model;

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
public class AnagraficaModel {
  private Integer id;
  private String name;
  private Date dataInizio;
  private Date dataFine;
  private String mansione;
  private Integer giorni;
  private String ftpt;
}

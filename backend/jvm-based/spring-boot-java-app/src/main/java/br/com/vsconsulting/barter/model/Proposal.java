package br.com.vsconsulting.barter.model;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "proposals")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Proposal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;
  @ManyToMany
  @JoinTable(name = "proprosal_offered_items",
             joinColumns =@JoinColumn(name="itemId"),
             inverseJoinColumns = @JoinColumn(name="proposalId"))
  private Set<Item> offeredItems;
  @ManyToMany
  @JoinTable(name = "proposal_target_items",
      joinColumns =@JoinColumn(name="itemId"),
      inverseJoinColumns = @JoinColumn(name="proposalId"))
  private Set<Item> targetItems;
  @Enumerated(EnumType.STRING)
  private ProposalStatus status;

}

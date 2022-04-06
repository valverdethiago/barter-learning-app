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
import javax.persistence.ManyToOne;
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
  @ManyToOne
  @JoinColumn(name = "owner_id")
  private User owner;
  @ManyToOne
  @JoinColumn(name = "requesting_user_id")
  private User requestingUser;
  @ManyToMany
  @JoinTable(name = "proposal_offered_items",
             joinColumns =@JoinColumn(name="item_id"),
             inverseJoinColumns = @JoinColumn(name="proposal_id"))
  private Set<Item> offeredItems;
  @ManyToMany
  @JoinTable(name = "proposal_requested_items",
      joinColumns =@JoinColumn(name="item_id"),
      inverseJoinColumns = @JoinColumn(name="proposal_id"))
  private Set<Item> requestedItems;
  @Enumerated(EnumType.STRING)
  private ProposalStatus status;

}

-- Table: proposal_target_items

-- DROP TABLE IF EXISTS proposal_target_items;

CREATE TABLE IF NOT EXISTS proposal_target_items
(
    item_id integer NOT NULL ,
    proposal_id integer NOT NULL,
    CONSTRAINT proprosal_target_items_pk PRIMARY KEY (item_id, proposal_id),
    CONSTRAINT proposaltargetitems_proposal_fk FOREIGN KEY (item_id)
        REFERENCES public.proposals (id),
    CONSTRAINT proposaltargetitems_item_fk FOREIGN KEY (proposal_id)
        REFERENCES public.items (id)
)

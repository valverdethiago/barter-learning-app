
-- Table: proposal_offered_items

-- DROP TABLE IF EXISTS proposal_offered_items;

CREATE TABLE IF NOT EXISTS proposal_offered_items
(
    item_id integer NOT NULL,
    proposal_id integer NOT NULL,
    CONSTRAINT proprosal_offered_items_pk PRIMARY KEY (item_id, proposal_id),
    CONSTRAINT proposaloffereditems_proposal_fk FOREIGN KEY (item_id)
        REFERENCES public.proposals (id),
    CONSTRAINT proposaloffereditems_item_fk FOREIGN KEY (proposal_id)
        REFERENCES public.items (id)
)

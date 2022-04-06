-- Table: proposal_requested_items

-- DROP TABLE IF EXISTS proposal_requested_items;

CREATE TABLE IF NOT EXISTS proposal_requested_items
(
    item_id integer NOT NULL ,
    proposal_id integer NOT NULL,
    CONSTRAINT proposal_requested_items_pk PRIMARY KEY (item_id, proposal_id),
    CONSTRAINT proposal_requested_items_proposal_fk FOREIGN KEY (proposal_id)
        REFERENCES public.proposals (id),
    CONSTRAINT proposal_requested_items_item_fk FOREIGN KEY (item_id)
        REFERENCES public.items (id)
)

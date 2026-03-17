ALTER TABLE t_lf_video
    ADD COLUMN category_id bigint(20) NULL;

ALTER TABLE t_lf_video
    ADD CONSTRAINT fk_video_category
    FOREIGN KEY (category_id) REFERENCES t_lf_category(id)
    ON DELETE SET NULL
    ON UPDATE CASCADE;
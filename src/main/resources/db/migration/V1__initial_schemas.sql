CREATE TABLE vg_dining_tables
(
    dt_dining_id  BIGINT AUTO_INCREMENT NOT NULL,
    dt_name       VARCHAR(50)           NOT NULL,
    dt_status     TINYINT               NULL,
    dt_created_at timestamp             NULL,
    dt_updated_at datetime              NULL,
    dt_deleted_at datetime              NULL,
    CONSTRAINT pk_vg_dining_tables PRIMARY KEY (dt_dining_id)
);

CREATE TABLE vg_items
(
    i_id          BIGINT AUTO_INCREMENT NOT NULL,
    i_name        VARCHAR(255)          NOT NULL,
    i_description LONGTEXT              NULL,
    i_status      TINYINT               NULL,
    i_created_at  timestamp             NULL,
    i_updated_at  timestamp             NULL,
    i_deleted_at  timestamp             NULL,
    i_created_by  BIGINT                NULL,
    i_updated_by  BIGINT                NULL,
    CONSTRAINT pk_vg_items PRIMARY KEY (i_id)
);

CREATE TABLE vg_order_items
(
    oi_id             BIGINT AUTO_INCREMENT NOT NULL,
    oi_qty            INT                   NULL,
    oi_item_id        BIGINT                NOT NULL,
    oi_prepareb_by_id BIGINT                NOT NULL,
    oi_status         VARCHAR(255)          NOT NULL,
    oi_active         BIT(1)                NOT NULL,
    oi_created_at     timestamp             NULL,
    CONSTRAINT pk_vg_order_items PRIMARY KEY (oi_id)
);

CREATE TABLE vg_orders
(
    o_id           BIGINT AUTO_INCREMENT NOT NULL,
    o_request_id   BIGINT                NOT NULL,
    o_status       TINYINT               NULL,
    o_palaced_at   timestamp             NULL,
    o_prepared_at  timestamp             NULL,
    o_completed_at timestamp             NULL,
    o_created_by   BIGINT                NULL,
    CONSTRAINT pk_vg_orders PRIMARY KEY (o_id)
);

CREATE TABLE vg_roles
(
    r_id         BIGINT AUTO_INCREMENT NOT NULL,
    r_role_name  VARCHAR(50)           NOT NULL,
    r_status     TINYINT               NULL,
    r_created_at timestamp             NULL,
    r_updated_at timestamp             NULL,
    r_deleted_at timestamp             NULL,
    r_created_by BIGINT                NULL,
    r_updated_by BIGINT                NULL,
    CONSTRAINT pk_vg_roles PRIMARY KEY (r_id)
);

CREATE TABLE vg_user_roles
(
    ur_role_id BIGINT NOT NULL,
    ur_user_id BIGINT NOT NULL,
    CONSTRAINT pk_vg_user_roles PRIMARY KEY (ur_role_id, ur_user_id)
);

CREATE TABLE vg_users
(
    u_id         BIGINT AUTO_INCREMENT NOT NULL,
    u_name       VARCHAR(255)          NULL,
    u_email      VARCHAR(255)          NOT NULL,
    u_password   VARCHAR(255)          NOT NULL,
    u_status     TINYINT               NULL,
    u_created_at timestamp             NULL,
    u_updated_at timestamp             NULL,
    u_deleted_at timestamp             NULL,
    u_created_by BIGINT                NOT NULL,
    u_updated_by BIGINT                NOT NULL,
    CONSTRAINT pk_vg_users PRIMARY KEY (u_id)
);

ALTER TABLE vg_order_items
    ADD CONSTRAINT uc_vg_order_items_oi_item UNIQUE (oi_item_id);

ALTER TABLE vg_order_items
    ADD CONSTRAINT uc_vg_order_items_oi_prepareb_by UNIQUE (oi_prepareb_by_id);

ALTER TABLE vg_orders
    ADD CONSTRAINT uc_vg_orders_o_created_by UNIQUE (o_created_by);

ALTER TABLE vg_orders
    ADD CONSTRAINT uc_vg_orders_o_request UNIQUE (o_request_id);

ALTER TABLE vg_orders
    ADD CONSTRAINT FK_VG_ORDERS_ON_O_CREATED_BY FOREIGN KEY (o_created_by) REFERENCES vg_users (u_id);

ALTER TABLE vg_orders
    ADD CONSTRAINT FK_VG_ORDERS_ON_O_REQUEST FOREIGN KEY (o_request_id) REFERENCES vg_users (u_id);

ALTER TABLE vg_order_items
    ADD CONSTRAINT FK_VG_ORDER_ITEMS_ON_OI_ITEM FOREIGN KEY (oi_item_id) REFERENCES vg_items (i_id);

ALTER TABLE vg_order_items
    ADD CONSTRAINT FK_VG_ORDER_ITEMS_ON_OI_PREPAREB_BY FOREIGN KEY (oi_prepareb_by_id) REFERENCES vg_users (u_id);

ALTER TABLE vg_user_roles
    ADD CONSTRAINT fk_vguserol_on_role FOREIGN KEY (ur_role_id) REFERENCES vg_roles (r_id);

ALTER TABLE vg_user_roles
    ADD CONSTRAINT fk_vguserol_on_user FOREIGN KEY (ur_user_id) REFERENCES vg_users (u_id);
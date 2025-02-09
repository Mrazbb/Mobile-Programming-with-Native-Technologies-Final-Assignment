CREATE TABLE "public"."tbl_quote" (

    -- IDENTIFIER
    "id" BIGINT NOT NULL,
    "quote" TEXT NOT NULL,
    "author" TEXT NOT NULL,
    "image" TEXT,

    -- TIMESTAMP
	"dtcreated" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"dtupdated" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "dtremoved" TIMESTAMP,

    -- CONSTRAINTS
    PRIMARY KEY ("id")
); 
CREATE TABLE "public"."tbl_rating" (

    -- IDENTIFIER
    "id" SERIAL NOT NULL,
    "quoteid" BIGINT NOT NULL,
    "userid" TEXT NOT NULL,
    "rating" INTEGER NOT NULL CHECK (rating >= 1 and rating <= 5),

    -- TIMESTAMP
	"dtcreated" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	"dtupdated" TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    "dtremoved" TIMESTAMP,

    -- CONSTRAINTS
    PRIMARY KEY ("id")
); 
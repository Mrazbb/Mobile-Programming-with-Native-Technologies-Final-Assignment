CREATE OR REPLACE VIEW public.view_quote AS
SELECT
    m.id,
    m.quote,
    m.author,
    m.image,
    m.dtcreated,
    m.dtupdated,
    m.dtremoved,
    (
        SELECT COALESCE(AVG(r.rating), 0)
        FROM public.tbl_rating AS r
        WHERE r.quoteid = m.id
    ) AS rating,

    (
      SELECT COALESCE(COUNT(r.rating), 0)
      FROM public.tbl_rating AS r
      WHERE r.quoteid = m.id
    ) AS number_of_ratings
FROM public.tbl_quote AS m;

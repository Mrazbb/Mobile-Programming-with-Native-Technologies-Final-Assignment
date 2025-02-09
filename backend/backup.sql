--
-- PostgreSQL database dump
--

-- Dumped from database version 16.3 (Debian 16.3-1.pgdg120+1)
-- Dumped by pg_dump version 16.3 (Debian 16.3-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: tbl_config; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_config (
    id text NOT NULL,
    type text NOT NULL,
    value text NOT NULL
);


ALTER TABLE public.tbl_config OWNER TO postgres;

--
-- Name: tbl_quote; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_quote (
    id bigint NOT NULL,
    quote text NOT NULL,
    author text NOT NULL,
    image text,
    dtcreated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    dtupdated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    dtremoved timestamp without time zone
);


ALTER TABLE public.tbl_quote OWNER TO postgres;

--
-- Name: tbl_rating; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tbl_rating (
    id integer NOT NULL,
    quoteid bigint NOT NULL,
    userid text NOT NULL,
    rating integer NOT NULL,
    dtcreated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    dtupdated timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    dtremoved timestamp without time zone,
    CONSTRAINT public_tbl_rating_rating_check CHECK (((rating >= 1) AND (rating <= 5)))
);


ALTER TABLE public.tbl_rating OWNER TO postgres;

--
-- Name: tbl_rating_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tbl_rating_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tbl_rating_id_seq OWNER TO postgres;

--
-- Name: tbl_rating_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tbl_rating_id_seq OWNED BY public.tbl_rating.id;


--
-- Name: view_quote; Type: VIEW; Schema: public; Owner: postgres
--

CREATE VIEW public.view_quote AS
 SELECT id,
    quote,
    author,
    image,
    dtcreated,
    dtupdated,
    dtremoved,
    ( SELECT COALESCE(avg(r.rating), (0)::numeric) AS "coalesce"
           FROM public.tbl_rating r
          WHERE (r.quoteid = m.id)) AS rating,
    ( SELECT COALESCE(count(r.rating), (0)::bigint) AS "coalesce"
           FROM public.tbl_rating r
          WHERE (r.quoteid = m.id)) AS number_of_ratings
   FROM public.tbl_quote m;


ALTER VIEW public.view_quote OWNER TO postgres;

--
-- Name: tbl_rating id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_rating ALTER COLUMN id SET DEFAULT nextval('public.tbl_rating_id_seq'::regclass);


--
-- Data for Name: tbl_config; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_config (id, type, value) FROM stdin;
\.


--
-- Data for Name: tbl_quote; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_quote (id, quote, author, image, dtcreated, dtupdated, dtremoved) FROM stdin;
1	The only limit to our realization of tomorrow is our doubts of today.	Franklin D. Roosevelt	/img/franklin_roosevelt.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
2	Do what you can, with what you have, where you are.	Theodore Roosevelt	/img/theodore_roosevelt.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
4	Happiness depends upon ourselves.	Aristotle	/img/aristotle.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
5	In the middle of every difficulty lies opportunity.	Albert Einstein	/img/albert_einstein.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
6	Do not dwell in the past, do not dream of the future, concentrate the mind on the present moment.	Buddha	/img/budha.png	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
7	Life is what happens when you’re busy making other plans.	John Lennon	/img/john_lennon.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
8	It always seems impossible until it is done.	Nelson Mandela	/img/nelson_mandela.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
9	We are what we repeatedly do. Excellence, then, is not an act, but a habit.	Will Durant	/img/will_durant.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
10	Act as if what you do makes a difference. It does.	William James	/img/william_james.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
3	Success is not final, failure is not fatal: it is the courage to continue that counts.	Winston Churchill	/img/winston_churchill.jpg	2025-02-08 12:09:49.156085	2025-02-08 12:09:49.156085	\N
\.


--
-- Data for Name: tbl_rating; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tbl_rating (id, quoteid, userid, rating, dtcreated, dtupdated, dtremoved) FROM stdin;
38	5	a5070ee586994fe3b802cf032cc367ca	3	2025-02-09 14:00:07.69247	2025-02-09 14:00:07.69247	\N
33	10	a5070ee586994fe3b802cf032cc367ca	4	2025-02-09 13:59:51.457971	2025-02-09 13:59:51.457971	\N
52	2	39954c62ab574d7995a343b402c3c398	5	2025-02-09 18:57:46.47353	2025-02-09 18:57:46.47353	\N
1	1	0bf062bb4c454fa6aee1a648b0954066	3	2025-02-09 11:01:18.347534	2025-02-09 11:01:18.347534	\N
37	4	a5070ee586994fe3b802cf032cc367ca	3	2025-02-09 14:00:06.388235	2025-02-09 14:00:06.388235	\N
6	4	0bf062bb4c454fa6aee1a648b0954066	4	2025-02-09 11:38:25.572319	2025-02-09 11:38:25.572319	\N
34	3	a5070ee586994fe3b802cf032cc367ca	3	2025-02-09 13:59:52.896006	2025-02-09 13:59:52.896006	\N
47	8	e8584f7431264620871f5b9510e1e8a4	4	2025-02-09 18:48:42.898171	2025-02-09 18:48:42.898171	\N
32	9	a5070ee586994fe3b802cf032cc367ca	3	2025-02-09 13:59:49.7671	2025-02-09 13:59:49.7671	\N
48	9	e8584f7431264620871f5b9510e1e8a4	3	2025-02-09 18:48:44.527807	2025-02-09 18:48:44.527807	\N
49	10	e8584f7431264620871f5b9510e1e8a4	3	2025-02-09 18:48:45.79668	2025-02-09 18:48:45.79668	\N
50	3	e8584f7431264620871f5b9510e1e8a4	5	2025-02-09 18:48:47.003211	2025-02-09 18:48:47.003211	\N
41	1	e8584f7431264620871f5b9510e1e8a4	2	2025-02-09 18:48:27.698722	2025-02-09 18:48:27.698722	\N
55	6	39954c62ab574d7995a343b402c3c398	3	2025-02-09 19:05:25.877131	2025-02-09 19:05:25.877131	\N
42	2	e8584f7431264620871f5b9510e1e8a4	3	2025-02-09 18:48:29.05089	2025-02-09 18:48:29.05089	\N
43	4	e8584f7431264620871f5b9510e1e8a4	5	2025-02-09 18:48:36.945192	2025-02-09 18:48:36.945192	\N
44	5	e8584f7431264620871f5b9510e1e8a4	3	2025-02-09 18:48:38.947245	2025-02-09 18:48:38.947245	\N
45	6	e8584f7431264620871f5b9510e1e8a4	2	2025-02-09 18:48:40.197039	2025-02-09 18:48:40.197039	\N
54	5	39954c62ab574d7995a343b402c3c398	3	2025-02-09 18:58:06.568521	2025-02-09 18:58:06.568521	\N
46	7	e8584f7431264620871f5b9510e1e8a4	4	2025-02-09 18:48:41.571662	2025-02-09 18:48:41.571662	\N
5	5	0bf062bb4c454fa6aee1a648b0954066	3	2025-02-09 11:09:57.757513	2025-02-09 11:09:57.757513	\N
4	2	0bf062bb4c454fa6aee1a648b0954066	4	2025-02-09 11:09:29.153937	2025-02-09 11:09:29.153937	\N
8	7	0bf062bb4c454fa6aee1a648b0954066	4	2025-02-09 11:42:11.679687	2025-02-09 11:42:11.679687	\N
9	8	0bf062bb4c454fa6aee1a648b0954066	4	2025-02-09 11:42:22.546444	2025-02-09 11:42:22.546444	\N
10	9	0bf062bb4c454fa6aee1a648b0954066	4	2025-02-09 11:42:24.271597	2025-02-09 11:42:24.271597	\N
11	10	0bf062bb4c454fa6aee1a648b0954066	4	2025-02-09 11:42:25.928482	2025-02-09 11:42:25.928482	\N
12	3	0bf062bb4c454fa6aee1a648b0954066	4	2025-02-09 11:42:27.917172	2025-02-09 11:42:27.917172	\N
7	6	0bf062bb4c454fa6aee1a648b0954066	4	2025-02-09 11:42:07.527318	2025-02-09 11:42:07.527318	\N
13	1	75c52b1fcdec4629b0e9876c00f83b6e	5	2025-02-09 13:56:30.13293	2025-02-09 13:56:30.13293	\N
14	2	75c52b1fcdec4629b0e9876c00f83b6e	3	2025-02-09 13:56:32.597863	2025-02-09 13:56:32.597863	\N
15	4	75c52b1fcdec4629b0e9876c00f83b6e	4	2025-02-09 13:56:39.594342	2025-02-09 13:56:39.594342	\N
16	5	75c52b1fcdec4629b0e9876c00f83b6e	4	2025-02-09 13:56:41.054393	2025-02-09 13:56:41.054393	\N
18	7	75c52b1fcdec4629b0e9876c00f83b6e	2	2025-02-09 13:56:44.169821	2025-02-09 13:56:44.169821	\N
19	8	75c52b1fcdec4629b0e9876c00f83b6e	3	2025-02-09 13:56:45.388071	2025-02-09 13:56:45.388071	\N
20	9	75c52b1fcdec4629b0e9876c00f83b6e	4	2025-02-09 13:56:46.823266	2025-02-09 13:56:46.823266	\N
21	10	75c52b1fcdec4629b0e9876c00f83b6e	5	2025-02-09 13:56:48.129844	2025-02-09 13:56:48.129844	\N
22	3	75c52b1fcdec4629b0e9876c00f83b6e	3	2025-02-09 13:56:49.284213	2025-02-09 13:56:49.284213	\N
17	6	75c52b1fcdec4629b0e9876c00f83b6e	3	2025-02-09 13:56:42.632565	2025-02-09 13:56:42.632565	\N
23	1	b31f24349ddf4660b6113495f91417c5	4	2025-02-09 13:57:33.77818	2025-02-09 13:57:33.77818	\N
24	2	b31f24349ddf4660b6113495f91417c5	5	2025-02-09 13:57:35.102657	2025-02-09 13:57:35.102657	\N
53	4	39954c62ab574d7995a343b402c3c398	4	2025-02-09 18:57:48.61147	2025-02-09 18:57:48.61147	\N
25	7	b31f24349ddf4660b6113495f91417c5	4	2025-02-09 13:57:38.830363	2025-02-09 13:57:38.830363	\N
27	6	1c3352674db2475ab0323cf61b15346d	4	2025-02-09 13:57:53.982793	2025-02-09 13:57:53.982793	\N
56	7	39954c62ab574d7995a343b402c3c398	4	2025-02-09 19:07:00.444703	2025-02-09 19:07:00.444703	\N
26	7	1c3352674db2475ab0323cf61b15346d	3	2025-02-09 13:57:49.295279	2025-02-09 13:57:49.295279	\N
28	8	1c3352674db2475ab0323cf61b15346d	4	2025-02-09 13:57:59.480348	2025-02-09 13:57:59.480348	\N
29	9	1c3352674db2475ab0323cf61b15346d	2	2025-02-09 13:58:01.110189	2025-02-09 13:58:01.110189	\N
30	10	1c3352674db2475ab0323cf61b15346d	4	2025-02-09 13:58:02.659799	2025-02-09 13:58:02.659799	\N
35	1	a5070ee586994fe3b802cf032cc367ca	3	2025-02-09 14:00:03.576626	2025-02-09 14:00:03.576626	\N
36	2	a5070ee586994fe3b802cf032cc367ca	4	2025-02-09 14:00:05.176579	2025-02-09 14:00:05.176579	\N
57	10	39954c62ab574d7995a343b402c3c398	1	2025-02-09 19:13:47.149009	2025-02-09 19:13:47.149009	\N
39	6	a5070ee586994fe3b802cf032cc367ca	5	2025-02-09 14:00:09.000505	2025-02-09 14:00:09.000505	\N
51	1	39954c62ab574d7995a343b402c3c398	3	2025-02-09 18:57:43.715467	2025-02-09 18:57:43.715467	\N
40	7	a5070ee586994fe3b802cf032cc367ca	3	2025-02-09 14:00:10.643376	2025-02-09 14:00:10.643376	\N
31	8	a5070ee586994fe3b802cf032cc367ca	4	2025-02-09 13:59:48.18002	2025-02-09 13:59:48.18002	\N
\.


--
-- Name: tbl_rating_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tbl_rating_id_seq', 57, true);


--
-- Name: tbl_config tbl_config_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_config
    ADD CONSTRAINT tbl_config_pkey PRIMARY KEY (id);


--
-- Name: tbl_quote tbl_quote_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_quote
    ADD CONSTRAINT tbl_quote_pkey PRIMARY KEY (id);


--
-- Name: tbl_rating tbl_rating_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tbl_rating
    ADD CONSTRAINT tbl_rating_pkey PRIMARY KEY (id);


--
-- Name: tbl_config_id_index; Type: INDEX; Schema: public; Owner: postgres
--

CREATE UNIQUE INDEX tbl_config_id_index ON public.tbl_config USING btree (id);


--
-- PostgreSQL database dump complete
--


(() => {

    const rem = parseFloat(getComputedStyle(document.documentElement).fontSize);

    const fade = (entries, appearOnScroll) => {
        entries.filter(entry => entry.isIntersecting).forEach(entry => {
            entry.target.classList.add("appearred");
            appearOnScroll.unobserve(entry.target);
        });
    };

    const appearOnScroll = new IntersectionObserver(fade, {threshold: 1});
    const appearOnScrollEarly = new IntersectionObserver(fade, {threshold: 0, rootMargin: `0px 0px -${10 * rem}px 0px`});

    document.querySelectorAll(".fade-in").forEach(element => appearOnScroll.observe(element));
    document.querySelectorAll(".fade-in-early").forEach(element => appearOnScrollEarly.observe(element));

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({behavior: 'smooth'});
        });
    });

})();
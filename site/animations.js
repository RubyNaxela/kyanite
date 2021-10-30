(() => {

    const fadeElements = document.querySelectorAll(".fade-in");
    const rem = parseFloat(getComputedStyle(document.documentElement).fontSize);

    const appearOnScroll = new IntersectionObserver((entries, appearOnScroll) => {
        entries.filter(entry => entry.isIntersecting).forEach(entry => {
            entry.target.classList.add("appearred");
            appearOnScroll.unobserve(entry.target);
        });
    }, {threshold: 1, rootMargin: `0px 0px -${10 * rem}px 0px`});

    fadeElements.forEach(element => appearOnScroll.observe(element));

    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function (e) {
            e.preventDefault();
            document.querySelector(this.getAttribute('href')).scrollIntoView({behavior: 'smooth'});
        });
    });

})();
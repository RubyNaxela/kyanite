/*
 * Copyright (c) 2022 Alex Pawelski
 *
 * Licensed under the Silicon License, Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   https://rubynaxela.github.io/Silicon-License/plain_text.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

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
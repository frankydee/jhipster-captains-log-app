/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CaptainsLogAppTestModule } from '../../../test.module';
import { ELRComponent } from 'app/entities/elr/elr.component';
import { ELRService } from 'app/entities/elr/elr.service';
import { ELR } from 'app/shared/model/elr.model';

describe('Component Tests', () => {
    describe('ELR Management Component', () => {
        let comp: ELRComponent;
        let fixture: ComponentFixture<ELRComponent>;
        let service: ELRService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CaptainsLogAppTestModule],
                declarations: [ELRComponent],
                providers: []
            })
                .overrideTemplate(ELRComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ELRComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ELRService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ELR(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.eLRS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
